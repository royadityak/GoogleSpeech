package com.jarvis.exploration;

/**
 * Created by Aditya Kumar Roy on 06/08/16.
 * Purpose - Exloring Google Speech API basic utilities
 */

// Google Cloud packages
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.protobuf.ByteString;
import com.google.cloud.speech.spi.v1.SpeechClient;
import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.cloud.speech.v1.RecognitionConfig;

// Java packages
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class SpeechRecognizer {

    public static void main(String[] args) throws Exception {

        //  Instantiating the Client API
        SpeechClient localSpClient = SpeechClient.create();

        // The first argument is the file which will be used as local content
        ByteString localSpContent = ByteString.copyFrom(Files.readAllBytes(Paths.get(args[0])));

        // Setting up the Recognition Configurations
        RecognitionConfig recConfigurations = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.LINEAR16).setLanguageCode("en-US").setSampleRateHertz(16000).build();

        // Instantiating the recognition audio builder
        RecognitionAudio recAudio = RecognitionAudio.newBuilder().setContent(localSpContent).build();

        // Speech translation
        List<SpeechRecognitionResult> speechRecLists = localSpClient.recognize(recConfigurations, recAudio).getResultsList();

        for (SpeechRecognitionResult res: speechRecLists) {
            List<SpeechRecognitionAlternative> srAltLists = res.getAlternativesList();
            for (SpeechRecognitionAlternative srAltList: srAltLists)
                System.out.println(srAltList.getTranscript());
        }
        // Quiting the speech client
        localSpClient.close();
    }
}
