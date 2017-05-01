package com.jarvis.exploration;

/**
 * Created by Aditya Kumar Roy on 06/08/16.
 * Purpose - Exloring Google Speech API basic utilities
 */

import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.protobuf.ByteString;
import com.google.cloud.speech.spi.v1.SpeechClient;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.RecognitionConfig;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class SpeechRecognizer {

    public static void main(String[] args) throws Exception {

        //  Creating an instance of the Speech Client API
        SpeechClient speechClient = SpeechClient.create();

        //  The path of the audio file to exploit
        String fileName = "/resources/steves_job_stanford_speech.raw";
        Path filePath = Paths.get(fileName);
        byte[] dataRead = Files.readAllBytes(filePath);
        ByteString speecBytes = ByteString.copyFrom(dataRead);

        // Requesting for the sync regnonitizer
        RecognitionConfig recognitionConfig = RecognitionConfig.newBuilder()
            .setEncoding(AudioEncoding.LINEAR16)
            .setLanguageCode("en-US")
            .setSampleRateHertz(16000)
            .build();

        //Recognition Audio builder
        RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
            .setContent(speecBytes)
            .build();

        // Translates the speech from the input file
        RecognizeResponse recognizeResponse = speechClient.recognize(recognitionConfig, recognitionAudio);
        List<SpeechRecognitionResult> outputLists = recognizeResponse.getResultsList();

        for (SpeechRecognitionResult result: outputLists) {
            List<SpeechRecognitionAlternative> alternativesLists = result.getAlternativesList();
            for (SpeechRecognitionAlternative alternativesList: alternativesLists) {
                System.out.printf("Transcription: %s%n", alternativesList.getTranscript());
            }
        }
        //Ending the Speech Client
        speechClient.close();
    }
}
