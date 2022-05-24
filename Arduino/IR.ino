#include <IRremote.h>
#include <LiquidCrystal.h>

LiquidCrystal lcd (12, 11, 5, 4, 3, 2);
int IRpin = 10;
int senzor = A0;
int senzorVstup;
float temp;

IRrecv irrecv(IRpin);
decode_results results;

void setup()
{
  lcd.begin(16, 2);
  irrecv.enableIRIn();
}

void loop()
{
  senzorVstup = analogRead(senzor);
  temp = (5.0 * senzorVstup * 100.0) / 1024.0;
  lcd.setCursor(0, 0);
  lcd.print("Teplota:");
  lcd.setCursor(7, 2);
  lcd.print(temp);
  if (temp > 35)
	digitalWrite(7, HIGH);
  else
	digitalWrite(7, LOW);
  delay(100);
  if (irrecv.decode(&results)) {
    unsigned int value = results.value;
    switch (value) {
      case 255:
	  	digitalWrite(7,HIGH);
      	delay(5000);
      	break;
     }
    irrecv.resume(); 
  }
}
