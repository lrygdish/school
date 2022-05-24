int led[] = {12, 11, 10, 9, 8, 7, 6};
int kocka = 0;

void setup()
{
  for(int i = 0; i < sizeof(led) / 2; i++)
    pinMode(led[i], OUTPUT);
  pinMode(4, INPUT);
  Serial.begin(9600);
}

void loop()
{
  if(digitalRead(4) != LOW) {
  	kocka = random(1, 7);
  	delay(100);
  	Serial.println(kocka);		//kontrola hodeného čísla
    Serial.println();
  }
  if(kocka != 0) {
    switch(kocka) {
      case 1:
          for(int i = 0; i < sizeof(led) / 2; i++)
            digitalWrite(led[i], LOW);
          digitalWrite(led[3], HIGH);
          break;
      case 2:
          for(int i = 0; i < sizeof(led) / 2; i++)
            digitalWrite(led[i], LOW);
          digitalWrite(led[2], HIGH);
          digitalWrite(led[4], HIGH);
          break;
      case 3:
          for(int i = 0; i < sizeof(led) / 2; i++)
            digitalWrite(led[i], LOW);
          for(int i = 0; i < sizeof(led) / 2; i++)
            if(led[i] >= 8 && led[i] <= 10)
              digitalWrite(led[i], HIGH);
          break;
      case 4:
          for(int i = 0; i < sizeof(led) / 2; i++)
            digitalWrite(led[i], LOW);
          for(int i = 0; i < sizeof(led) / 2; i++)
            if(led[i] % 2 != 1)
              digitalWrite(led[i], HIGH);
          break;
      case 5:
          for(int i = 0; i < sizeof(led) / 2; i++)
            digitalWrite(led[i], LOW);
          for(int i = 0; i < sizeof(led) / 2; i++)
            if(led[i] % 2 != 1 || led[i] == 9)
              digitalWrite(led[i], HIGH);
          break;
      case 6:
          for(int i = 0; i < sizeof(led) / 2; i++)
            digitalWrite(led[i], LOW);
          for(int i = 0; i < sizeof(led) / 2; i++)
            if(led[i] != 9)
              digitalWrite(led[i], HIGH);
          break;	
    }
  }
  delay(150);
}
