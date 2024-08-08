#include "com_lieineyes_calculator_model_ModelCalculator.h"
#include "../calculatorcpp/model_calculator.h"
#include "../calculatorcpp/valid_user_input.h"
#include <iostream>

JNIEXPORT jstring JNICALL Java_com_lieineyes_calculator_model_ModelCalculator_validInputExpression (JNIEnv *env, jobject obj, jstring inputExpression) {
  const char* inputExpressionCPP = env->GetStringUTFChars(inputExpression, NULL);
  S21::ValidUserInput valid_input(inputExpressionCPP);
  std::string error = valid_input.Input();
  return env->NewStringUTF(error.c_str());
}

JNIEXPORT jobjectArray JNICALL Java_com_lieineyes_calculator_model_ModelCalculator_calculate
  (JNIEnv * env, jobject obj, jstring inputExpression, jstring valueX) {

    const char* inputExpressionCPP = env->GetStringUTFChars(inputExpression, NULL);
    const char* valueXCPP = env->GetStringUTFChars(valueX, NULL);

    S21::ModelCalculator temp(inputExpressionCPP, valueXCPP);
    std::string errorCPP = "0";
    double resultCPP = 0.0;
    temp.Calculate(resultCPP, errorCPP);

    jobjectArray retobjarr = (jobjectArray)env->NewObjectArray(2, env->FindClass("java/lang/Object"), NULL);
    env->SetObjectArrayElement(retobjarr, 0, newDouble(env, resultCPP));
    env->SetObjectArrayElement(retobjarr, 1, env->NewStringUTF(errorCPP.c_str()));

    return retobjarr;
}

jobject newDouble(JNIEnv* env, double value){
    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID doubleConstructor = env->GetMethodID(doubleClass, "<init>", "(D)V");
    return env->NewObject(doubleClass, doubleConstructor, static_cast<jdouble>(value));
}
