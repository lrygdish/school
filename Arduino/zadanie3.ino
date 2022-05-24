int led[] = {13, 12, 11, 10, 9};

void setup()
{
  for(int i = 0; i < sizeof(led) / 2; i++)	
    pinMode(led[i], OUTPUT);
}

void loop()
{
  for(int i = 0; i < sizeof(led) / 2; i++) {
    digitalWrite(led[i], HIGH);
    delay(80);
    digitalWrite(led[i], LOW);
  }
  for(int i = (sizeof(led) / 2) - 1; i >= 0; i--) {
    digitalWrite(led[i], HIGH);
    delay(80);
    digitalWrite(led[i], LOW);
  }
}
