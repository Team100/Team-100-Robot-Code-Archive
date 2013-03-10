#include "WPILib.h"
#include "Multiplexer.h"

Multiplexer::Multiplexer()
{
	 DigitalModule *module = DigitalModule::GetInstance(1);
	if (module)
	{
		m_i2c = module->GetI2C(kAddress);
	}
}

/**
 * Destructor.
 */
Multiplexer::~Multiplexer()
{
	delete m_i2c;
	m_i2c = NULL;
}

/*
 * reads the arduino and recieves 2 bytes to represent the digital inputs attached to it
 * saves the 2 bytes to 'loByte' and 'hiByte'
 * return: true if unseccessful
 */
bool Multiplexer::readArduino()
{
	UINT8 buffer[3];
	bool result;

	result = m_i2c ->Transaction(0,0,buffer,2);
//	printf(" Transaction result=%d, buffer[]=", result);
	dataRead.loByte = buffer [0];
	dataRead.hiByte = buffer [1];
	dataRead.successful = result;
	if(!result)
	{
		//printf("%x, %x\n", buffer[0], buffer[1]);
	}
	else
	{
		//printf("ERROR: can not recieve data from arduino");
	}

	return result;
}

/*
 * reads the value of the digital input of the specified channel
 * param 1: the channel to be read
 * param 2: a bool varible to hold the sucess of the function
 * return: value of the specified digital input
 */
bool Multiplexer::getInput(int channelID, bool *success)
{
	uint8_t temp;
	
	if(channelID>13 || channelID<0)
	{
		printf("ERROR: invalid ID must be between 0 and 13");
		*success = false;
		return false;
	}
	*success = true;
	
	if (channelID < 8)
	{
		temp = dataRead.loByte &(1 << channelID);
		return !temp == 0;
	}
	else
	{
		temp=dataRead.hiByte &(1 << channelID-8);
		return !temp == 0;
	}
}
