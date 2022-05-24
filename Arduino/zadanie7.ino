int led = 13, btn = 12;
bool status_old = false, status_new = false;

void setup()
{
  pinMode(led, OUTPUT);
  pinMode(btn, INPUT);
}

void loop()
{
  status_new = digitalRead(btn);
  if(status_new != status_old) {
   if(status_new == true){
     if(digitalRead(led) == LOW)	digitalWrite(led, HIGH);
     else	digitalWrite(led, LOW);
   }
  }
  status_old = status_new;
}
