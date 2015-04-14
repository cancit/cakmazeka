package com.cuyazilim.voice;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.speech.tts.*;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import java.util.*;

public class MainActivity extends Activity implements OnInitListener{
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;

	private EditText metTextHint;
	private ListView mlvTextMatches;
	private Spinner msTextMatches;
	private Button mbtSpeak;
	private TextToSpeech tts;
	String ad="Adını bilmiyorum";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		metTextHint = (EditText) findViewById(R.id.etTextHint);
		mlvTextMatches = (ListView) findViewById(R.id.lvTextMatches);
		msTextMatches = (Spinner) findViewById(R.id.sNoOfMatches);
		mbtSpeak = (Button) findViewById(R.id.btSpeak);
		checkVoiceRecognition();
		tts = new TextToSpeech(this, this);
		tts.setSpeechRate((float)0.3);
		Konus("Merhaba");
	}

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
																	RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			mbtSpeak.setEnabled(false);
			mbtSpeak.setText("Voice recognizer not present");
			Toast.makeText(this, "Voice recognizer not present",
						   Toast.LENGTH_SHORT).show();
		}
	}

	public void speak(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
Konus("Merhaba");
		// Specify the calling package to identify your application
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
						.getPackage().getName());

		// Display an hint to the user about what he should say.
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText()
						.toString());

		// Given an hint to the recognizer about what the user is going to say
		//There are two form of language model available
		//1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
		//2.LANGUAGE_MODEL_FREE_FORM  : If not sure about the words or phrases and its domain.
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
						RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

		// If number of Matches is not selected then return show toast message
		if (msTextMatches.getSelectedItemPosition() == AdapterView.INVALID_POSITION) {
			Toast.makeText(this, "Please select No. of Matches from spinner",
						   Toast.LENGTH_SHORT).show();
			return;
		}

		int noOfMatches = Integer.parseInt(msTextMatches.getSelectedItem()
										   .toString());
		// Specify how many results you want to receive. The results will be
		// sorted where the first result is the one with higher confidence.
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		//Start the Voice recognizer activity for the result.
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

		//If Voice recognition is successful then it returns RESULT_OK
			if(resultCode == RESULT_OK) {

				ArrayList<String> textMatchList = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if (!textMatchList.isEmpty()) {
					// If first Match contains the 'search' word
					// Then start web search.
					if (textMatchList.get(0).contains("ara")) {

						String searchQuery = textMatchList.get(0);
						searchQuery = searchQuery.replace("ara","");
						Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
						search.putExtra(SearchManager.QUERY, searchQuery);
						startActivity(search);
					} else {
						// populate the Matches
						mlvTextMatches
							.setAdapter(new ArrayAdapter<String>(this,
																 android.R.layout.simple_list_item_1,
																 textMatchList));
					}
					if (textMatchList.get(0).contains("tost")) {

						String searchQuery = textMatchList.get(0);
						searchQuery = searchQuery.replace("tost","");
						Toast.makeText(MainActivity.this,searchQuery,Toast.LENGTH_SHORT).show();
					}
					if (textMatchList.get(0).contains("benim adım ne")) {

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						Toast.makeText(MainActivity.this,"Senin adın " +ad,Toast.LENGTH_SHORT).show();
						Konus("Senin adin " + ad);
					}else if (textMatchList.get(0).contains("benim adım")) {

						String searchQuery = textMatchList.get(0);
						searchQuery = searchQuery.replace("benim adım","");
						ad=searchQuery;
						Konus("Merhaba " +searchQuery);
						Toast.makeText(MainActivity.this,"Merhaba " +searchQuery+"!",Toast.LENGTH_SHORT).show();
					}
					if (textMatchList.get(0).contains("senin adın")) {

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("Henüz bir adım yok");
					}
					if (textMatchList.get(0).contains("espiri patlat")) {

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						//Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("Booooooooooooooooooooooom");
					}
					if (textMatchList.get(0).contains("selamın aleyküm")) {

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						//Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("Aleykum selam");
					}
					if (textMatchList.get(0).contains("ananı")) {

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						//Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("is o dereceye gelirse ben de senin anani");
					}
					if (textMatchList.get(0).contains("kere")&& (textMatchList.get(0).contains("kaç eder"))) {
						//6 kere 9 kaç eder
						int a=textMatchList.get(0).indexOf("kere");//2
						int b=textMatchList.get(0).indexOf(" ",a+5);//8
						int c=textMatchList.get(0).indexOf(" ",1);//
					//	Toast.makeText(MainActivity.this,a+" "+b+" "+c + textMatchList.get(0).subSequence(a+5,b).toString(),Toast.LENGTH_SHORT).show();
						int say1=Integer.parseInt(textMatchList.get(0).subSequence(0,c).toString());
						int say2=Integer.parseInt(textMatchList.get(0).subSequence(a+5,b).toString());
						Konus(String.valueOf(say1*say2));
					}	
					if (textMatchList.get(0).contains("artı")&& (textMatchList.get(0).contains("kaç eder"))) {
						//6 kere 9 kaç eder
						int a=textMatchList.get(0).indexOf("artı");//2
						int b=textMatchList.get(0).indexOf(" ",a+5);//8
						int c=textMatchList.get(0).indexOf(" ",1);//
					//	Toast.makeText(MainActivity.this,a+" "+b+" "+c + textMatchList.get(0).subSequence(a+5,b).toString(),Toast.LENGTH_SHORT).show();
						int say1=Integer.parseInt(textMatchList.get(0).subSequence(0,c).toString());
						int say2=Integer.parseInt(textMatchList.get(0).subSequence(a+5,b).toString());
						Konus(String.valueOf(say1+say2));
					}		
					if (textMatchList.get(0).contains("eksi")&& (textMatchList.get(0).contains("kaç eder"))) {
						//6 kere 9 kaç eder
						int a=textMatchList.get(0).indexOf("eksi");//2
						int b=textMatchList.get(0).indexOf(" ",a+5);//8
						int c=textMatchList.get(0).indexOf(" ",1);//
						//	Toast.makeText(MainActivity.this,a+" "+b+" "+c + textMatchList.get(0).subSequence(a+5,b).toString(),Toast.LENGTH_SHORT).show();
						int say1=Integer.parseInt(textMatchList.get(0).subSequence(0,c).toString());
						int say2=Integer.parseInt(textMatchList.get(0).subSequence(a+5,b).toString());
						Konus(String.valueOf(say1-say2));
					}
					if (textMatchList.get(0).contains("bölü")&& (textMatchList.get(0).contains("kaç eder"))) {
						//6 kere 9 kaç eder
						int a=textMatchList.get(0).indexOf("bölü");//2
						int b=textMatchList.get(0).indexOf(" ",a+5);//8
						int c=textMatchList.get(0).indexOf(" ",1);//
						//	Toast.makeText(MainActivity.this,a+" "+b+" "+c + textMatchList.get(0).subSequence(a+5,b).toString(),Toast.LENGTH_SHORT).show();
						int say1=Integer.parseInt(textMatchList.get(0).subSequence(0,c).toString());
						int say2=Integer.parseInt(textMatchList.get(0).subSequence(a+5,b).toString());
						Konus(String.valueOf(say1/say2));
					}	
					if (textMatchList.get(0).contains("en uzun kelime ne")) {

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						//Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("çekoslavakyalılaştırabildiklerimizlerdenmisiniz");
					}
					if ((textMatchList.get(0).contains("sesin neden bozuk")) || (textMatchList.get(0).contains("sesin neden böyle")) ){

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						//Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("Nerem doğru ki");
					}	
					if (textMatchList.get(0).contains("şarkı söyle")){

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						//Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("ankaranın bağları da büklüm büklüm yolları ne zaman sarhoş oldum da kaldıramıyom kolları");
					}
					if (textMatchList.get(0).contains("senin sesine ne olmuş")){

						//	String searchQuery = textMatchList.get(0);
						//searchQuery = searchQuery.replace("benim adım","");
						//Toast.makeText(MainActivity.this,"Henüz bir adım yok",Toast.LENGTH_SHORT).show();
						Konus("Noooooooooolmus");
					}
				}
				//Result code for various error.
			}else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
				showToastMessage("Audio Error");
			}else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
				showToastMessage("Client Error");
			}else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
				showToastMessage("Network Error");
			}else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
				showToastMessage("No Match");
			}else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
				showToastMessage("Server Error");
			}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * Helper method to show the toast message
	 **/
	void showToastMessage(String message){
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onInit(int code) {
		if (code==TextToSpeech.SUCCESS) {
			tts.setLanguage(Locale.getDefault());
		} else {
			tts = null;
			Toast.makeText(this, "Failed to initialize TTS engine.", Toast.LENGTH_SHORT).show();
		}
	}
	public void Konus(String text){
		if (tts!=null) {
			if (!tts.isSpeaking()) {
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			}
		}
	}
	@Override
	protected void onDestroy() {
		if (tts!=null) {
			tts.stop();
            tts.shutdown();
		}
		super.onDestroy();
	}

}
