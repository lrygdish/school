int senzor,
	PinGas = A5,
	zadymenie = 150;

void setup()
{
  pinMode(13, OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(PinGas, INPUT);
  pinMode(9, OUTPUT);
}

void loop()
{
  senzor = analogRead(PinGas);
  if(senzor > zadymenie) {
    digitalWrite(10, LOW);
    digitalWrite(13, HIGH);
    tone(9, 1000, 200);
  }
  else
  {
    digitalWrite(13, LOW);
    digitalWrite(10, HIGH);
    noTone(9);
  }
  delay(10);
}
