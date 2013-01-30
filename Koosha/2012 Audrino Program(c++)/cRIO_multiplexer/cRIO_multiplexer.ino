/*
  DigitalReadSerial
 Reads a digital input on pin 2, prints the result to the serial monitor 
 
 This example code is in the public domain.
 */
 
#include <Wire.h>

uint8_t hiByte;
uint8_t loByte;

void setup() {
  //Serial.begin(9600);
  Wire.onRequest(requestEvent); // register event
  Wire.begin(50);                // join i2c bus with address 100 (50*2)
  for(int i=0; i<14; i++)
  {
    pinMode(i, INPUT);
    digitalWrite(i, HIGH);
  }
}

void loop()
{
  uint8_t temp;
  int i;
  
  temp=1;
  for(i=13; i>=8; i--)
  {
    temp=temp*2; 
    temp+=digitalRead(i);
  }
  hiByte=temp;
  
  temp=0;
  for(i=7; i>=0; i--)
  {
    temp=temp*2;
    temp+=digitalRead(i);
//    Serial.print("temp=");
//    Serial.print(temp, HEX);
//    Serial.print(" i=");
//    Serial.print(i);
//    Serial.print("\n\r");
  }
  loByte=temp;
  
//  Serial.print(loByte, HEX);
//  Serial.print(" ");
//  Serial.println(hiByte, HEX);
}

// function that executes whenever data is requested by master
// this function is registered as an event, see setup()
void requestEvent()
{
  uint8_t buf[2] = {loByte, hiByte};
  Wire.write (buf,2);
}
