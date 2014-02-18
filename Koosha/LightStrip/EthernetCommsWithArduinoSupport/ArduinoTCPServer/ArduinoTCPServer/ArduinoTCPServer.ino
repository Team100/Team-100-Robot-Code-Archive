//` Simple Arduino server to get messages from cRIO 

#include <Ethernet.h>
// the sensor communicates using SPI, so include the library:
#include <SPI.h>


// assign a MAC address for the ethernet controller.
// fill in your address here:
byte mac[] = { 
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
// assign an IP address for the controller:
IPAddress ip(10,1,0,100);
IPAddress gateway(10,1,0,1);	
IPAddress subnet(255, 0, 0, 0);


int ServerPort=10000;

// Initialize the Ethernet server library
// with the IP address and port you want to use 
EthernetServer server(ServerPort);


void setup() {
  // start the SPI library:
  //SPI.begin();

  Serial.begin(9600);
  // start the Ethernet connection and the server:
  Ethernet.begin(mac, ip);
  server.begin();
  Serial.println("Server Started...");
}

void loop() { 
  // listen for incoming Ethernet connections:
//  listenForEthernetClients();
  // listen for incoming clients
  EthernetClient client = server.available();
  if (client) {
    Serial.println("Got a client");
    if (client.connected() && client.available()) {
      char c = client.read();
      client.print(c);
      Serial.println(c);
    }
//    switch c:
//      case 0:
      
    // give the web browser time to receive the data
    delay(1);
    // close the connection:
    if(!client.connected()) {
      client.stop();
    }
  }
}

//void listenForEthernetClients() {
//  // listen for incoming clients
//  EthernetClient client = server.available();
//  if (client) {
//    Serial.println("Got a client");
//    while (client.connected()) {
//      if (client.available()) {
//        char c = client.read();
//        client.print(c);
//        Serial.println(c);
//      }
//    }
//    // give the web browser time to receive the data
//    delay(1);
//    // close the connection:
//    client.stop();
//  }
//} 

