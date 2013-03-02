#include "WPILib.h"
#include "Multiplexer.h"
#include "Math.h"

/**
 * This is a demo program showing the use of the RobotBase class.
 * The SimpleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 */ 
class RobotDemo : public SimpleRobot
{
	RobotDrive myRobot; // robot drive system
	Joystick stick; // only joystick
	Timer clock;
	Multiplexer arduino;
	Joystick logitech;
	CANJaguar motor;
	

public:
	RobotDemo(void):
		myRobot(1, 2),	// these must be initialized in the same order
		stick(1),		// as they are declared above.
		clock(),
		arduino(),
		logitech(2),
		motor(2)
	{
		myRobot.SetExpiration(0.1);
	}
	
	void MotorSpeed(void)
	{
		clock.Reset();
		clock.Start();
		Preferences *motorval = Preferences::GetInstance();
		float mapper=0;
		float value;
		bool pre=false;
		while(IsDisabled())
		{
			value=(logitech.GetY()+mapper);
			motorval -> PutDouble("motor_speed", value);
			if(logitech.GetRawButton(2))
			{
				if(!pre)
				{
					mapper=(1)*(value);
				}
			}
			pre=logitech.GetRawButton(2);
			if(clock.Get()>0.5)
			{
				printf("%f\n", value);
				clock.Reset();
			}
		}
		motorval -> Save();
		printf("%f", value);
	}
	
	void Disabled(void)
	{
		MotorSpeed();
	}
	
	/**
	 * Drive left & right motors for 2 seconds then stop
	 */
	void Autonomous(void)
	{
		//Preferences *tRexArm_setpnt = Preferences::GetInstance();
		myRobot.SetSafetyEnabled(false);
		myRobot.Drive(0.5, 0.0); 	// drive forwards half speed
		Wait(2.0); 				//    for 2 seconds
		myRobot.Drive(0.0, 0.0); 	// stop robot
		printf("done safe to disable");
	}

	/**
	 * Runs the motors with arcade steering. 
	 */
	void OperatorControl(void)
	{
		Preferences *tRexArm_setpnt = Preferences::GetInstance();
		bool *pbool;
		clock.Reset();
		clock.Start();
		tRexArm_setpnt -> PutDouble("down_setpoint", 0.5);
		tRexArm_setpnt -> PutDouble("high_setpoint", 1.75);
		//tRexArm_setpnt -> Save();
		myRobot.SetSafetyEnabled(true);
		Preferences *motorval = Preferences::GetInstance();
		clock.Reset();
		clock.Start();
		while (IsOperatorControl())
		{
			motor.Set(motorval -> GetDouble("motor_speed", 0.0));
			if(clock.Get()>=0.5)
			{
				//value=arduino.readArduino();
				//printf("%d\n", arduino.getInput(2, pbool));
				//m_i2c -> Read(0, 6, &buffer[0]);
				clock.Reset();
				//printf("%d\n", value);
			}
			myRobot.ArcadeDrive(stick); // drive with arcade style (use right stick)
			Wait(0.005);				// wait for a motor update time
			if(IsDisabled())
			{
				MotorSpeed();
			}
		}
		if(IsDisabled())
		{
			MotorSpeed();
		}
	}
};

START_ROBOT_CLASS(RobotDemo);

