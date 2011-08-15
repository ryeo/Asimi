/**
    AndroidControlledBot - The bot that can be controlled by Android phone

    Part of the Sumi project - http://sudarmuthu.com/arduino/sumi

   Copyright 2011  Sudar Muthu  (email : sudar@sudarmuthu.com)

/*
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <sudar@sudarmuthu.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer or coffee in return - Sudar
 * ----------------------------------------------------------------------------
 */

/**
 * Requres the following Arduino libraries
 *
 * MissileLauncher - http://sudarmuthu.com/arduino/missilelauncher
 * USB Host Shield Library v2.0 - https://github.com/felis/USB_Host_Shield_2.0
 * MeetAndroid (Amarino) - http://amarino.googlecode.com/svn/trunk/arduino_library/MeetAndroid
 *
 */

#include <SumiBot.h>
#include <MeetAndroid.h>

// declare MeetAndroid so that you can call functions with it
MeetAndroid meetAndroid;
SumiBot sumiBot;

void setup() {
    // use the baud rate your bluetooth module is configured to
    // not all baud rates are working well, i.e. ATMEGA168 works best with 57600
    Serial.begin(115200);

    // initialize Sumi bot
    sumiBot.setEnablePins(2, 3);
    sumiBot.setControlPins(4, 5, 6, 7);

    // register callback functions, which will be called when an associated event occurs.
    meetAndroid.registerFunction(handleBot, 'b');
//    meetAndroid.registerFunction(handleMissile, 'm'); //TODO: Enable missile as well

}

void loop() {
    meetAndroid.receive(); // you need to keep this in your loop() to receive events
}

void handleBot(byte flag, byte num) {
    int direction[1];

    meetAndroid.getIntValues(direction);

    switch (direction[0]) {
        case 0: // LEFT
            sumiBot.turnLeft();
            break;

        case 1: // Right
            sumiBot.turnRight();
            break;

        case 2:  // Up
            sumiBot.moveForward();
            break;

        case 3:  // down
            sumiBot.moveBackward();
            break;

        case 4:  // stop
            sumiBot.stop();
            break;

    }
}
