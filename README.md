# Socio-Geographical influences on COVID-19 Mortality Rates through the lens of Healthcare Accessibility.


# Datasets

- [CSSE Covid 19 Data](https://github.com/CSSEGISandData/COVID-19/tree/master/csse_covid_19_data/csse_covid_19_daily_reports)

  Database schema: FIPS,Admin2,Province_State,Country_Region,Last_Update,Lat,Long_,Confirmed,Deaths,Recovered,Active,Combined_Key,Incident_Rate,Case_Fatality_Ratio

  Step 1: Data Cleaning
    - Keep only data from the united states
    - Keep only required columns: Province_State, Confirmed, Deaths, Recovered, Incident_Rate, Case_Fatality_Ratio
    - Remove the line which do not have all these data
    - Removing rows that have null values for state names (as these data will be ambigious in our analysis)
    - Combined for each day all the recorded data from within the same state into one record ie. there were multiple recorded cases from different cities within the same day for the same state. They were combined into one as we are interested into state-wise analysis

    - Files: Clean.java, CleanMapper.java, CleanReducer.java
    - Input: dt2202_nyu_edu/proj/input/covid_19_data.csv, Output: dt2202_nyu_edu/proj/input/covid_19_data_cleaned.csv

  Step 2: Data Profiling
    - Checking for inconsistencies and removing them (ie date ranges)
    - Making sure only 50 different values are present as state names in the data
    - For other null values, they are filled with the average from the previous date and the next date, as a close estimate.
    - Checking some statistics, outliers in our data

    - Files: dataprofile.scala
    - Input: dt2202_nyu_edu/proj/input/covid_19_data_cleaned.csv (hadoop), Output: dataprofile_output.txt (next to the scala file)

# How to run

- give execute permission to run.sh script file using `chmod +x run.sh`
- use ./run.sh command to run the shell script which executes the mapreduce job
- follow the same process to run chean.sh script which cleans the files after job execution is completed