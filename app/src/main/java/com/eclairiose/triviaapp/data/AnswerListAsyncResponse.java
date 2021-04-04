package com.eclairiose.triviaapp.data;

import com.eclairiose.triviaapp.model.Questions;

import java.util.ArrayList;
import java.util.List;

public interface AnswerListAsyncResponse {
    void processFinised(ArrayList<Questions> arrayList);
}
