/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.parsing.IUtility;
import edu.wpi.first.wpilibj.util.BoundaryException;
import java.util.TimerTask;

/**
 * Class implements a PID Control Loop.
 *
 * Creates a separate thread which reads the given PIDSource and takes care of
 * the integral calculations, as well as writing the given PIDOutput
 */
public class CustomPIDController implements IUtility {

    public static final double kDefaultPeriod = 0.01; //called every 1 ms
    private double m_P;			// factor for "proportional" control
    private double m_I;			// factor for "integral" control
    private double m_D;			// factor for "derivative" control
    private double m_F;                 // factor for feedforward
    private double m_maximumOutput = 12.0;	// |maximum output|
    private double m_minimumOutput = -12.0;	// |minimum output|
    private double m_maximumInput = 1000.0;		// maximum input - limit setpoint to this
    private double m_minimumInput = -1000.0;		// minimum input - limit setpoint to this
    private boolean m_enabled = false; 			//is the pid controller enabled
    private double m_prevError = 0.0;	// the prior sensor input (used to compute velocity)
    private double m_totalError = 0.0; //the sum of the errors for use in the integral calc
    private double m_tolerance = 0.05;	//the percetage error that is considered on target
    private double m_positSetpoint = 0.0;
    private double m_velocSetpoint = 0.0;
    private double m_error = 0.0;
    private double m_result = 0.0;
    private double m_period = kDefaultPeriod;
    PIDSource m_pidInput;
    PIDOutput m_pidOutput;
    java.util.Timer m_controlLoop;
    private double m_gearRatio = 0.0;
    public double m_filteredPosition = 0.0; 
    private double m_maxVeloc; //max possible velocity

    private class PIDTask extends TimerTask {

        private CustomPIDController m_controller;

        public PIDTask(CustomPIDController controller) {
            if (controller == null) {
                throw new NullPointerException("Given PIDController was null");
            }
            m_controller = controller;
        }

        public void run() {
            m_controller.calculate();
        }
    }

    /**
     * Allocate a PID object with the given constants for P, I, D
     *
     * @param Kp the proportional coefficient
     * @param Ki the integral coefficient
     * @param Kd the derivative coefficient
     * @param source The PIDSource object that is used to get values
     * @param output The PIDOutput object that is set to the output value
     * @param period the loop time for doing calculations. This particularly
     * effects calculations of the integral and differential terms.
     */
    public CustomPIDController(PIDSource source, PIDOutput output, double gearRatio, double period) {
        if (source == null) {
            throw new NullPointerException("Null PIDSource was given");
        }
        if (output == null) {
            throw new NullPointerException("Null PIDOutput was given");
        }
        m_controlLoop = new java.util.Timer();
        
        m_P = 0.0;
        m_I = 0.0;
        m_D = 0.0;
        m_F = 0.0;

        m_pidInput = source;
        m_pidOutput = output;
        m_gearRatio = gearRatio;
        m_maxVeloc = 0.0;
        m_period = period;

        m_controlLoop.schedule(new PIDTask(this), 0L, (long) (m_period * 1000));
    }

    /**
     * Allocate a PID object with the given constants for P, I, D, using a 1ms
     * period.
     *
     * @param Kp the proportional coefficient
     * @param Ki the integral coefficient
     * @param Kd the derivative coefficient
     * @param Kf the feed-forward constant
     * @param source The PIDSource object that is used to get values
     * @param output The PIDOutput object that is set to the output value
     */
    public CustomPIDController(PIDSource source, PIDOutput output, double gearRatio) {
        this(source, output, gearRatio,kDefaultPeriod);
    }//creates PID object

    public void setMaxVeloc(double m){
        m_maxVeloc = m;
    }//gets max velocity as set by SmartDashboard
    
    /**
     * Free the PID object
     */
    public void free() {
        m_controlLoop.cancel();
        m_controlLoop = null;
    }

    /**
     * Read the input, calculate the output accordingly, and write to the
     * output. This should only be called by the PIDTask and is created during
     * initialization.
     */
    private void calculate() {
        boolean enabled;
        PIDSource pidInput;

        synchronized (this) {
            if (m_pidInput == null) {
                return;
            }
            if (m_pidOutput == null) {
                return;
            }
            enabled = m_enabled; // take snapshot of these values...
            pidInput = m_pidInput;
        }

        if (enabled) {
            double input = pidInput.pidGet() / m_gearRatio;
            double result;
            PIDOutput pidOutput = null;
    
            //rectangular motion profile
            if(m_positSetpoint-m_filteredPosition > m_maxVeloc*kDefaultPeriod){
                m_filteredPosition += m_maxVeloc*kDefaultPeriod;
                m_velocSetpoint = m_maxVeloc;
                
            }
            else if(m_positSetpoint-m_filteredPosition < -m_maxVeloc*kDefaultPeriod){
                m_filteredPosition -= m_maxVeloc*kDefaultPeriod;
                m_velocSetpoint = -m_maxVeloc;
            }
            else{
                m_velocSetpoint = (m_positSetpoint-m_filteredPosition)/kDefaultPeriod;
                m_filteredPosition = m_positSetpoint;
            }
            
            synchronized (this) {
                m_error = m_filteredPosition - input;


//                if (((m_totalError + m_error) * m_I < m_maximumOutput)
//                        && ((m_totalError + m_error) * m_I > m_minimumOutput)) {
//                    m_totalError += m_error;
//                }
                
                //cap integral term
                m_totalError += m_error;
                if (m_I != 0.0) {
                    double m_max = (m_maximumOutput / m_I);
                    if ((m_max) < m_totalError) {
                        m_totalError = m_max;
                    }
                    if ((-m_max > m_totalError)) {
                        m_totalError = -m_max;
                    }
                }
                
                //algorithm!
                m_result = (m_P * m_error + m_I * m_totalError - m_D * (m_velocSetpoint - (m_error - m_prevError)/kDefaultPeriod));
                //m_result += (m_filteredPosition * m_F);
                m_prevError = m_error;

                if (m_result > m_maximumOutput) {
                    m_result = m_maximumOutput;
                } else if (m_result < m_minimumOutput) {
                    m_result = m_minimumOutput;
                }
                pidOutput = m_pidOutput;
                result = m_result;
            }

            pidOutput.pidWrite(result);
        }
    }

    /**
     * Set the PID Controller gain parameters. Set the proportional, integral,
     * and differential coefficients.
     *
     * @param p Proportional coefficient
     * @param i Integral coefficient
     * @param d Differential coefficient
     */
    public synchronized void setPID(double p, double i, double d, double f) {
        m_P = p;
        m_I = i;
        m_D = d;
        m_F = f;
    }

    /**
     * Get the Proportional coefficient
     *
     * @return proportional coefficient
     */
    public double getP() {
        return m_P;
    }

    /**
     * Get the Integral coefficient
     *
     * @return integral coefficient
     */
    public double getI() {
        return m_I;
    }

    /**
     * Get the Differential coefficient
     *
     * @return differential coefficient
     */
    public synchronized double getD() {
        return m_D;
    }

    public synchronized double getF() {
        return m_F;
    }

    /**
     * Return the current PID result This is always centered on zero and
     * constrained the the max and min outs
     *
     * @return the latest calculated output
     */
    public synchronized double get() {
        return m_result;
    }

    /**
     * Sets the maximum and minimum values expected from the input.
     *
     * @param minimumInput the minimum value expected from the input
     * @param maximumInput the maximum value expected from the output
     */
    public synchronized void setInputRange(double minimumInput, double maximumInput) {
        if (minimumInput > maximumInput) {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        m_minimumInput = minimumInput;
        m_maximumInput = maximumInput;
        //setSetpoint(m_positSetpoint, m_velocSetpoint);
    }

    /**
     * Sets the minimum and maximum values to write.
     *
     * @param minimumOutput the minimum value to write to the output
     * @param maximumOutput the maximum value to write to the output
     */
    public synchronized void setOutputRange(double minimumOutput, double maximumOutput) {
        if (minimumOutput > maximumOutput) {
            throw new BoundaryException("Lower bound is greater than upper bound");
        }
        m_minimumOutput = minimumOutput;
        m_maximumOutput = maximumOutput;
    }

    /**
     * Set the setpoint for the PIDController
     *
     * @param setpoint the desired setpoint
     */
    public synchronized void setSetpoint(double position, double velocity) {
        m_positSetpoint = position;
        m_velocSetpoint = velocity;
        //        if (m_maximumInput > m_minimumInput) {
//            if (setpoint > m_maximumInput) {
//                m_positSetpoint = m_maximumInput;
//            } else if (setpoint < m_minimumInput) {
//                m_positSetpoint = m_minimumInput;
//            } else {
//                m_positSetpoint = setpoint;
//            }
//        } else {
//            m_positSetpoint = setpoint;
//        }
    }
    public synchronized void setPositionSetpoint(double position) {
        m_positSetpoint = position;
    }
    /**
     * Returns the current setpoint of the PIDController
     *
     * @return the current setpoint
     */
    public synchronized double getSetpoint() {
        return m_positSetpoint;
    }

    /**
     * Returns the current difference of the input from the setpoint
     *
     * @return the current error
     */
    public synchronized double getError() {
        return m_error;
    }

    public synchronized double getTotalError() {
        return m_totalError;
    }

    /**
     * Set the percentage error which is considered tolerable for use with
     * OnTarget. (Input of 15.0 = 15 percent)
     *
     * @param percent error which is tolerable
     */
    public synchronized void setTolerance(double percent) {
        m_tolerance = percent;
    }

    /**
     * Return true if the error is within the percentage of the total input
     * range, determined by setTolerance. This assumes that the maximum and
     * minimum input were set using setInput.
     *
     * @return true if the error is less than the tolerance
     */
    public synchronized boolean onTarget() {
        return (Math.abs(m_error) < m_tolerance / 100
                * (m_maximumInput - m_minimumInput));
    }

    /**
     * Begin running the PIDController
     */
    public synchronized void enable() {
        m_enabled = true;
    }

    /**
     * Stop running the PIDController, this sets the output to zero before
     * stopping.
     */
    public synchronized void disable() {
        m_pidOutput.pidWrite(0);
        m_enabled = false;
    }

    /**
     * Return true if PIDController is enabled.
     */
    public synchronized boolean isEnable() {
        return m_enabled;
    }

    /**
     * Reset the previous error,, the integral term, and disable the controller.
     */
    public synchronized void reset() {
        disable();
        m_prevError = 0;
        m_totalError = 0;
        m_result = 0;
    }
}
