#include <LiquidCrystal.h>
#include <Keypad.h>
#define Password_Length 4

LiquidCrystal lcd(A0, A1, A2, A3, A4, A5);
const byte ROWS = 4; 
const byte COLS = 4;

char hexaKeys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

byte rowPins[ROWS] = {9, 8, 7, 6};
byte colPins[COLS] = {5, 4, 3, 2};

char Data[Password_Length]; 
char Master[Password_Length] = "123"; 
byte data_count = 0, master_count = 0;
char customKey;

Keypad customKeypad = Keypad(makeKeymap(hexaKeys), 
                             rowPins, colPins, ROWS, COLS);

void setup()
{
  lcd.begin(16, 2);
  lcd.setCursor(0, 0);
  lcd.clear();
}

void loop(){

  lcd.setCursor(0,0);
  lcd.print("Zadajte heslo:");

  customKey = customKeypad.getKey();
  if (customKey){
    Data[data_count] = customKey; 
    lcd.setCursor(data_count,1); 
    lcd.print(Data[data_count]); 
    data_count++; 
    }

  if(data_count == Password_Length-1){
    lcd.clear();

    if(!strcmp(Data, Master)){
      lcd.print("Spravne");
      delay(2000);
      }
    else{
      lcd.print("Nespravne");
      delay(1000);
      }
    
    lcd.clear();
    clearData();  
  }
}

void clearData(){
  while(data_count !=0){
    Data[data_count--] = 0; 
  }
  return;
}
