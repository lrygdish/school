#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
float tempC = 0;

void setup()
{
  pinMode(A0, INPUT);
  lcd.begin(16, 2);
  lcd.setCursor(0, 0);
  lcd.print("Teplota: ");
  lcd.setCursor(0, 1);
}

void loop()
{
	tempC = (double) ((analogRead(A0) / 1024.0) * 5 - 0.5) * 100;
  	lcd.setCursor(0, 1);
  	lcd.print(tempC);
  	delay(1000);
}
