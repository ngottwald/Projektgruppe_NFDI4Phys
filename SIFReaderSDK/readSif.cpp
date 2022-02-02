//---------------------------------------------------------------------------

#pragma hdrstop

#include "ATSIFIO.h"
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <stdio.h>
using namespace std;

//---------------------------------------------------------------------------

void retrieveErrorCode(unsigned int _ui_ret, char * sz_error);
void retrievePropertyType(ATSIF_PropertyType _propType, char * sz_propertyType);
int getUserInput();

#pragma argsused
int main(int argc, char* argv[])
{
  AT_32 at32_userInput;
  AT_U32 atu32_ret, atu32_noFrames, atu32_frameSize, atu32_noSubImages;
  char *  sz_fileName = new char[MAX_PATH];
  char * sz_xmlFileName = new char[MAX_PATH];
  char *  sz_error = new char[MAX_PATH];
  float * imageBuffer;
  size_t strLength;
  memset(sz_fileName, '\0', MAX_PATH);
  memset(sz_error, '\0', MAX_PATH);

  if (sizeof(argv) > 1 && argv[1] != nullptr) {
      sz_fileName = argv[1];

      if (argv[2] != nullptr) {
          sz_xmlFileName = argv[2];
      }
      else {
        sz_xmlFileName = "sif.xml";
      }

    
      char *  sz_propertyName = new char[MAX_PATH];
      memset(sz_propertyName, '\0', MAX_PATH);
      char *  sz_propertyValue = new char[MAX_PATH];
      memset(sz_propertyValue, '\0', MAX_PATH);
      char *  sz_propertyType = new char[MAX_PATH];
      memset(sz_propertyType, '\0', MAX_PATH);

      char* propertyNames[108] = { "Type", "Active", "Version", "Time", "Formatted Time", "FileName", "Temperature","UnstabalizedTemperature","Head", 
      "HeadModel", "StoreType" ,"DataType" ,"SIDisplacement" ,"SINumberSubFrames","PixelReadOutTime", "TrackHeight", "ReadPattern", "ReadPatternFullName",
      "ShutterDelay", "CentreRow", "RowOffset", "Operation", "Mode", "ModeFullName", "TriggerSource", "TriggerSourceFullName", "TriggerLevel", "ExposureTime",
      "Delay", "IntegrationCycleTime", "NumberIntegrations", "KineticCycleTime", "FlipX", "FlipY", "Clock", "AClock", "IOC", "Frequency", "NumberPulses",
      "FrameTransferAcquisitionMode", "BaselineClamp", "PreScan", "EMRealGain", "BaselineOffset", "SWVersion", "SWVersionEx", "MCP", "Gain", "VerticalClockAmp",
      "VerticalShiftSpeed", "OutputAmplifier", "PreAmplifierGain", "Serial", "DetectorFormatX", "DetectorFormatZ", "NumberImages", "NumberSubImages",
      "SubImageHBin", "SubImageVBin", "SubImageLeft", "SubImageRight", "SubImageTop", "SubImageBottom", "Baseline", "CCDLeft", "CCDRight", "CCDTop", "CCDBottom",
      "Sensitivity", "DetectionWavelength", "CountConvertMode", "IsCountConvert", "XAxisType", "XAxisUnit", "YAxisType", "YAxisUnit", "ZAxisType", "ZAxisUnit",
      "UserText", "IsPhotonCountingEnabled", "NumberThresholds", "Threshold1", "Threshold2", "Threshold3", "Threshold4", "AveragingFilterMode", "AveragingFactor",
      "FrameCount", "NoiseFilter", "Threshold", "TimeStamp", "OutputAEnabled", "OutputAWidth", "OutputADelay", "OutputAPolarity", "OutputBEnabled",
      "OutputBWidth", "OutputBDelay", "OutputBPolarity", "OutputCEnabled", "OutputCWidth", "OutputCDelay", "OutputCDelay", "GateMode", "GateWidth", "GateDelay",
      "GateDelayStep", "GateWidthStep"};

      atu32_ret = ATSIF_SetFileAccessMode(ATSIF_ReadHeaderOnly);
      if(atu32_ret != ATSIF_SUCCESS) {
        printf("Could not set File access Mode. Error: %u\n", atu32_ret);
      }
      else {
        printf("Try to open file: ");
        printf(sz_fileName);
        printf("\n");
        atu32_ret = ATSIF_ReadFromFile(sz_fileName);
        if(atu32_ret != ATSIF_SUCCESS) {
          printf("Could not open File : %s.\nError: %u\n", sz_fileName,atu32_ret);
        }

        ofstream xmlSifFile;
        xmlSifFile.open(sz_xmlFileName);
        xmlSifFile << "<?xml version=<\"1.0\" encoding=\"UTF - 8\"?>\n";
        
        xmlSifFile << "<properties>\n";

        ATSIF_PropertyType pType;
        // Show all properties
        for (int i = 0; i < 108; i++) {
            atu32_ret = ATSIF_GetPropertyValue(ATSIF_Signal, propertyNames[i], sz_propertyValue, MAX_PATH);
            if (atu32_ret != ATSIF_SUCCESS) {
                retrieveErrorCode(atu32_ret, sz_error);
                printf("Could not get Property Value '%s'.\nError code : %u\nError Message : %s\n\n", propertyNames[i], atu32_ret, sz_error);
            }
            else {
                // printf("Property: \"%s\"  has Value : \"%s\"\n", propertyNames[i],sz_propertyValue);
            }
            atu32_ret = ATSIF_GetPropertyType(ATSIF_Signal, propertyNames[i], &pType);
            if (atu32_ret != ATSIF_SUCCESS) {
                retrieveErrorCode(atu32_ret, sz_error);
                printf("Could not get Property Type of '%s'.\nError code : %u\nError Message : %s\n\n", propertyNames[i], atu32_ret, sz_error);
            }
            else {
                retrievePropertyType(pType, sz_propertyType);
                // printf("Property Type : %s\n\n", sz_propertyType);
            }
            xmlSifFile << "\t<property name=\"";
            xmlSifFile << propertyNames[i];
            xmlSifFile << "\" value=\"";
            xmlSifFile << sz_propertyValue;
            xmlSifFile << "\" type=\"";
            xmlSifFile << sz_propertyType;
            xmlSifFile << "\"/>\n";
        }
        
        xmlSifFile << "</properties>\n";
        
        xmlSifFile.close();
        printf("\n");
        printf("Data successfully written into ");
        printf(sz_xmlFileName);
        printf("\n");
        
      }
  }
  else {
  printf("No filename given. Application will terminate.\n\n");
  }
  
  //delete[] sz_fileName;
  //delete[] sz_xmlFileName;
  system("PAUSE");
        return 0;
}
//---------------------------------------------------------------------------

void retrieveErrorCode(unsigned int _ui_ret, char * sz_error) {

  int paramNotValid(0);
  switch(_ui_ret) {
    case(ATSIF_SIF_FORMAT_ERROR):
      _snprintf(sz_error, MAX_PATH, "SIF FORMAT ERROR");
      break;
    case(ATSIF_NO_SIF_LOADED):
      _snprintf(sz_error, MAX_PATH, "SIF NOT LOADED");
      break;
    case(ATSIF_FILE_NOT_FOUND):
      _snprintf(sz_error, MAX_PATH, "FILE NOT FOUND");
      break;
    case(ATSIF_FILE_ACCESS_ERROR):
      _snprintf(sz_error, MAX_PATH, "FILE ACCESS ERROR");
      break;
    case(ATSIF_DATA_NOT_PRESENT):
      _snprintf(sz_error, MAX_PATH, "SIF DATA NOT PRESENT");
      break;
    case(ATSIF_P8INVALID):
      ++paramNotValid;
    case(ATSIF_P7INVALID):
      ++paramNotValid;
    case(ATSIF_P6INVALID):
      ++paramNotValid;
    case(ATSIF_P5INVALID):
      ++paramNotValid;
    case(ATSIF_P4INVALID):
      ++paramNotValid;
    case(ATSIF_P3INVALID):
      ++paramNotValid;
    case(ATSIF_P2INVALID):
      ++paramNotValid;
    case(ATSIF_P1INVALID):
      ++paramNotValid;
      _snprintf(sz_error, MAX_PATH, "PARAMETER %i NOT VALID", paramNotValid);
      break;
    default:
      sz_error = "UNKNOWN ERROR";
      break;
  }
}

void retrievePropertyType(ATSIF_PropertyType _propType, char * sz_propertyType) {

  switch(_propType) {
    case(ATSIF_AT_8):
      _snprintf(sz_propertyType, MAX_PATH, "char");
      break;
    case(ATSIF_AT_U8):
      _snprintf(sz_propertyType, MAX_PATH, "unsigned char");
      break;
    case(ATSIF_AT_32):
      _snprintf(sz_propertyType, MAX_PATH, "bool/int/long");
      break;
    case(ATSIF_AT_U32):
      _snprintf(sz_propertyType, MAX_PATH, "unsigned int/unsigned long");
      break;
    case(ATSIF_Float):
      _snprintf(sz_propertyType, MAX_PATH, "float");
      break;
    case(ATSIF_Double):
      _snprintf(sz_propertyType, MAX_PATH, "double");
      break;
    case(ATSIF_String):
      _snprintf(sz_propertyType, MAX_PATH, "char * ");
      break;
    default:
      _snprintf(sz_propertyType, MAX_PATH, "unknown");
      break;
  }
}

int getUserInput() {

  AT_32 choice;
  printf("\nSIFIO Example Menu  : \n\n");
  printf("\t1. Test File load.\n");
  printf("\t2. Test Properties.\n");
  printf("\t0. Exit.\n\n");

  scanf("%i", &choice);

  return choice;
}

