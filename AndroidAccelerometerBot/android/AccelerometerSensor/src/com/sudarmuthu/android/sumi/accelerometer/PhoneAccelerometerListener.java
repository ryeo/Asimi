/**
 * Part of the Sumi project - http://sudarmuthu.com/arduino/sumi
 * 
 * Copyright 2011  Sudar Muthu  (email : sudar@sudarmuthu.com)
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <sudar@sudarmuthu.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer or coffee in return - Sudar
 * ----------------------------------------------------------------------------
 */

package com.sudarmuthu.android.sumi.accelerometer;

/**
 * Interface to check if the phone has changed direction
 * 
 * @author Sudar (http://sudarmuthu.com)
 *
 */
public interface PhoneAccelerometerListener {

	public void onPhoneDirectionChanged(BotDirection newDirection);
}

/**
 * Different directions of the bot
 * 
 * @author Sudar (http://sudarmuthu.com)
 *
 */
enum BotDirection {
	LEFT,
	RIGHT,
	UP,
	DOWN,
	STOP
}