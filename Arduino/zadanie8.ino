int led = 11, button1 = 13, button2 = 12;
int krok = 5, jas = 0;
bool status1 = 0, status2 = 0;


void setup()
{
  pinMode(led, OUTPUT);
  pinMode(button1, INPUT);
  pinMode(button2, INPUT);
}

void loop()
{
  status1 = digitalRead(button1);
  status2 = digitalRead(button2);
  analogWrite(led, jas);
  if(status1) {	
    jas = jas + krok;
    if(jas > 255)	jas = jas - krok;
    delay(80);
  }
  if(status2) {	
    jas = jas - krok;
    if(jas < 0)		jas = jas + krok;
    delay(20);
  }
}
