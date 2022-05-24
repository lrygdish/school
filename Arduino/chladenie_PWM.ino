#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
float tempC = 0;
int pwm = 0;
int motor = 9;

void setup()
{
  pinMode(A0, INPUT);
  pinMode(motor, OUTPUT);
  lcd.begin(16, 2);
  lcd.setCursor(0, 0);
  lcd.print("Teplota: ");
  lcd.setCursor(0, 1);
}

void loop()
{
	tempC = (double) ((analogRead(A0) / 1024.0) * 5 - 0.5) * 100;
  	pwm = map(analogRead(A0), 0, 1023, 0, 700);
  	if(tempC > 25)
      analogWrite(motor, pwm);
  	else
      analogWrite(motor, 0);
  	lcd.setCursor(0, 1);
  	lcd.print(tempC);
  	delay(1000);
}
