//The MIT License (MIT)

//

//Copyright (c) 2014 Damien Auffret

//

//Permission is hereby granted, free of charge, to any person obtaining a copy

//of this software and associated documentation files (the "Software"), to deal

//in the Software without restriction, including without limitation the rights

//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell

//copies of the Software, and to permit persons to whom the Software is

//furnished to do so, subject to the following conditions:

//

//The above copyright notice and this permission notice shall be included in all

//copies or substantial portions of the Software.

//

//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR

//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,

//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE

//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER

//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,

//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE

//SOFTWARE.

#include <IRremote.h>
#include <Adafruit_NeoPixel.h>

#define PIN 6

Adafruit_NeoPixel strip = Adafruit_NeoPixel(16, PIN, NEO_GRB + NEO_KHZ800);


byte byteArray[8]= {
  7,0,1,2,3,4,0,0};

// Boutons
int button1 = 12;
int button2 = 13;

//Vibreur
int vibreur = 10; 

//Creation comm IR :
// Pour envoyer
IRsend irsend; // pin 3 par default (cf library)

// Pour recevoir
int RECV_PIN = A0;
IRrecv irrecv(RECV_PIN);

// gestion du cycle I/O IR
int compt=0;

// Pour enregistrer la  couleur pour flashColor
byte memRed=0;
byte memGreen=0;
byte memBlue=0;

byte memByte= 0;   
// Gestion de notre IP
//byte myIP[] = {1, 2, 3, 4};// Pour tests
byte myIP[] = {54, 2, 3, 4};// Stockage de l'IP du portbale associé 
unsigned long int LongIntIP = ( myIP[0]*16777216 + myIP[1] * 65536 + myIP[2] * 256  +  myIP[3] );// Conversion en long pour l'émission en IR

// Initialisation
void setup() {
  Serial.begin(115200); // Com BT avec le portable
  irrecv.enableIRIn(); // Activation de la reception IR
  pinMode(5,OUTPUT);
  pinMode(button1,INPUT);
  pinMode(button2,INPUT);
  pinMode(vibreur,OUTPUT);
  strip.begin();
  strip.show(); // Initialize all pixels to 'off'
    Serial.begin(115200);  // The Bluetooth Mate defaults to 115200bps
   Serial.print("$");  // Print three times individually
   Serial.print("$");
   Serial.print("$");  // Enter command mode
   delay(100);  // Short delay, wait for the Mate to send back CMD
   Serial.println("SI,0x800");
   delay(100);  // Temporarily Change the baudrate to 9600, no parity
   Serial.print("$");  // Print three times individually
   Serial.print("$");
   Serial.print("$");  // Enter command mode
   delay(100);  // Short delay, wait for the Mate to send back CMD
   Serial.println("SJ,0x800");  // Temporarily Change the baudrate to 9600, no parity
   // 115200 can be too fast at times for NewSoftSerial to relay the data reliably
   
}

#define RECEPTION_IP 4

void loop() {

  // Partie communication avec telephonne
  if (Serial.available() > 0) {

    int i;
    int taille = Serial.read();
    byte recieveTable[taille];
    // Reception des messages du protable

    for (i=0; i < taille; i++) {
      recieveTable[i]=Serial.read();
    }

    // Traitement 
    switch (recieveTable[0]) {
      // Recoit l'IP du portbale et le stocke dans myIP[]
    case RECEPTION_IP:
      myIP[0]=recieveTable[1];
      myIP[1]=recieveTable[2];
      myIP[2]=recieveTable[3];
      myIP[3]=recieveTable[4];
      LongIntIP = ( myIP[0]*16777216 + myIP[1] * 65536 + myIP[2] * 256  +  myIP[3] );
      break;
      // Allume d'une couleur fixe la led : R G B
    case 2:

      memRed = recieveTable[1];
      memGreen = recieveTable[2];
      memBlue = recieveTable[3];    
      colorWipe(strip.Color((int) memRed,(int) memGreen,(int) memBlue), 1); 
      break;
      // Flash : vitesse de clignotement , nombre de cycle
    case 3:
      theaterChase(strip.Color((int)memRed, (int)memGreen, (int)memBlue),((int)recieveTable[1])*100,(int)recieveTable[2]);
      colorWipe(strip.Color(0,0,0), 1); 
      break;
      // Vibreur : time
    case 1 :
      vibrer(recieveTable[1]);
      break;
    default:
      break;
    }
  }

  // read the state of the pushbutton value:
  int buttonState1 = digitalRead(button1);
  int buttonState2 = digitalRead(button2);


  // Recevoir un nouvel IP d'un autre bracelet
  if(compt % 10 != 0){
    unsigned long recieve = 0; //Donnée recue lors du bump (sur 4 bytes)
    decode_results results;
    if (irrecv.decode(&results)) {
      recieve = results.value;
      //Serial.println(results.value,DEC);
      irrecv.resume(); // Recevoir une nouvelle valeur

      byte* reveive_byte = (byte*) &recieve;

      if(recieve != 0 ){

        byteArray[0] = 7;
        byteArray[1] = 0;
        byteArray[2] = reveive_byte[3];
        byteArray[3] = reveive_byte[2];
        byteArray[4] = reveive_byte[1];
        byteArray[5] = reveive_byte[0];
        byteArray[6] = 0;
        byteArray[7] = 0;
      //////////////////////////////////////////////////  
        //if(byteArray[2] == myIP[0]){
        Serial.write(byteArray,8);
        /}
      ////////////////////////////////////////////////
        // Pour debugage :
        //Serial.println(byteArray[2],DEC);
        //Serial.println(byteArray[3],DEC);
        //Serial.println(byteArray[4],DEC);
        //Serial.println(byteArray[5],DEC);
      }
    }

  }
  else{

    if(buttonState2 == 1){
      irsend.sendSony(LongIntIP,32); // Envoi de l'IP
      delay(100);
      irrecv.enableIRIn();
    }



  }  
  compt++;
  delay(100);




  if(buttonState1 == 1){
    byte tab[]={
      1,1    };
    Serial.write(tab,2);
    rainbowCycle(20);
    colorWipe(strip.Color(0,0,0), 1); 


  }

}

// Vibreur
void vibrer(int duree){
  analogWrite(vibreur, 255);
  delay(duree*1000);
  analogWrite(vibreur, 0);


}

// Input a value 0 to 255 to get a color value.
// The colours are a transition r - g - b - back to r.
uint32_t Wheel(byte WheelPos) {
  if(WheelPos < 85) {
    return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
  } 
  else if(WheelPos < 170) {
    WheelPos -= 85;
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  } 
  else {
    WheelPos -= 170;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
}

void colorWipe(uint32_t c, uint8_t wait) {
  for(uint16_t i=0; i<strip.numPixels(); i++) {
    strip.setPixelColor(i, c);
    strip.show();
    delay(wait);
  }
}

//Theatre-style crawling lights.
void theaterChase(uint32_t c, uint8_t wait, int cycle) {
  for (int j=0; j<cycle; j++) {  //do 10 cycles of chasing
    for (int q=0; q < 3; q++) {
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, c);    //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}


void rainbow(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256; j++) {
    for(i=0; i<strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel((i+j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

//Theatre-style crawling lights with rainbow effect
void theaterChaseRainbow(uint8_t wait) {
  for (int j=0; j < 256; j++) {     // cycle all 256 colors in the wheel
    for (int q=0; q < 3; q++) {
      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, Wheel( (i+j) % 255));    //turn every third pixel on
      }
      strip.show();

      delay(wait);

      for (int i=0; i < strip.numPixels(); i=i+3) {
        strip.setPixelColor(i+q, 0);        //turn every third pixel off
      }
    }
  }
}

// Slightly different, this makes the rainbow equally distributed throughout
void rainbowCycle(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}

