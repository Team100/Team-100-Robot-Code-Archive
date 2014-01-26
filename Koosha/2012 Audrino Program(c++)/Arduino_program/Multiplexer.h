#ifndef __MULTIPLEXER_h__
#define __MULTIPLEXER_h__

#include "SensorBase.h"

class I2C;

class Multiplexer: public SensorBase
{
protected:
	static const UINT8 kAddress = 0x64;	//adress is 100 for team 100 ofcourse
	
public:
	struct AllInputs
	{
		UINT8 hiByte;
		UINT8 loByte;
		bool successful;
	};

public:
	Multiplexer();
	~Multiplexer();
	bool readArduino();
	bool getInput(int channelID, bool *success);

protected:
	I2C* m_i2c;
	AllInputs dataRead;
};

#endif
