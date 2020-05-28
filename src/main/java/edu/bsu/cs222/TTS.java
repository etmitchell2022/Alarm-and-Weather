package edu.bsu.cs222;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.Locale;

class TTS {
    //Lines 17 - 31 of this code came from Geeks For Geeks
    void readTTS(String testString) {
            WeatherAPI weatherAPI = new WeatherAPI();
            weatherAPI.APIAccess();
            String temp = weatherAPI.printTemp();
            String chanceOfRain = weatherAPI.printChanceOfRain();
            String precipitationType = weatherAPI.printPrecipitationType();
            String precipitationIntensity = weatherAPI.printPrecipitationIntensity();
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
            Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
            Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
            synthesizer.allocate();
            synthesizer.resume();
            synthesizer.speakPlainText(testString, null);
            synthesizer.speakPlainText(temp, null);
            synthesizer.speakPlainText(chanceOfRain, null);
            synthesizer.speakPlainText(precipitationType, null);
            synthesizer.speakPlainText(precipitationIntensity, null);
            synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synthesizer.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
