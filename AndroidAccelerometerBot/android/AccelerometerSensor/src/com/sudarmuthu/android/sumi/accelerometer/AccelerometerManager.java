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

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Android Accelerometer Sensor Manager Archetype. Based on the code by antoine vianey
 * 
 * @author Sudar (http://sudarmuthu.com)
 *
 */
public class AccelerometerManager {
	
	private static Sensor sensor;
	private static SensorManager sensorManager;
	private static PhoneAccelerometerListener listener;
	
	/** indicates whether or not Accelerometer Sensor is supported */
	private static Boolean supported;
	/** indicates whether or not Accelerometer Sensor is running */
	private static boolean running = false;
	
	private static BotDirection currentDirection = null; 
	
	/**
	 * Returns true if the manager is listening to orientation changes
	 */
	public static boolean isListening() {
		return running;
	}
	
	/**
	 * Unregisters listeners
	 */
	public static void stopListening() {
		running = false;
		try {
			if (sensorManager != null && sensorEventListener != null) {
				sensorManager.unregisterListener(sensorEventListener);
			}
		} catch (Exception e) {}
	}
	
	/**
	 * Returns true if at least one Accelerometer sensor is available
	 */
	public static boolean isSupported() {
		if (supported == null) {
			if (Accelerometer.getContext() != null) {
				sensorManager = (SensorManager) Accelerometer.getContext().
						getSystemService(Context.SENSOR_SERVICE);
				List<Sensor> sensors = sensorManager.getSensorList(
						Sensor.TYPE_ACCELEROMETER);
				supported = new Boolean(sensors.size() > 0);
			} else {
				supported = Boolean.FALSE;
			}
		}
		return supported;
	}

	/**
	 * Registers a listener and start listening
	 * @param accelerometerListener
	 * 			callback for accelerometer events
	 */
	public static void startListening(
			PhoneAccelerometerListener accelerometerListener) {
		sensorManager = (SensorManager) Accelerometer.getContext().
				getSystemService(Context.SENSOR_SERVICE);
		List<Sensor> sensors = sensorManager.getSensorList(
				Sensor.TYPE_ACCELEROMETER);
		if (sensors.size() > 0) {
			sensor = sensors.get(0);
			running = sensorManager.registerListener(
					sensorEventListener, sensor, 
					SensorManager.SENSOR_DELAY_GAME);
			listener = accelerometerListener;
		}
	}
	
	/**
	 * Configures threshold and interval
	 * And registers a listener and start listening
	 * @param accelerometerListener
	 * 			callback for accelerometer events
	 * @param threshold
	 * 			minimum acceleration variation for considering shaking
	 * @param interval
	 * 			minimum interval between to shake events
	 */
	public static void startListening(
			PhoneAccelerometerListener accelerometerListener, 
			int threshold, int interval) {
		startListening(accelerometerListener);
	}

	/**
	 * When the bot's direction is changed
	 * 
	 * @param newDirection
	 */
	private static void changeDirection(BotDirection newDirection) {
		if (currentDirection != newDirection) {
			currentDirection = newDirection;
    		// trigger change event
    		listener.onPhoneDirectionChanged(newDirection);			
		}
	}

	/**
	 * The listener that listen to events from the accelerometer listener
	 */
	private static SensorEventListener sensorEventListener = 
		new SensorEventListener() {

		private float x = 0;
		private float y = 0;
		private float z = 0;
		
		public void onSensorChanged(SensorEvent event) {
    		x = event.values[0];
			y = event.values[1];
			z = event.values[2];

			if (y < -2) {
				changeDirection(BotDirection.LEFT);
				return;
			} else if (y > 2) {
				changeDirection(BotDirection.RIGHT);
				return;
			}

			if (z < 2) {
				changeDirection(BotDirection.DOWN);
			} else if (z > 2) {
				changeDirection(BotDirection.UP);
			}			
		}

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			//dummy
		}
	};
}
