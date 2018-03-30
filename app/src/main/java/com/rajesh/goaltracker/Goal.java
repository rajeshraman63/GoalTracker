package com.rajesh.goaltracker;


public class Goal {
    private String goalName;
    private String goalDescription;
    private String startDate;
    private String endDate;
   // private int progress;

    public Goal() {
    }

    public Goal(String goalName, String goalDescription, String startDate, String endDate) {
        this.goalName = goalName;
        this.goalDescription = goalDescription;
        this.startDate = startDate;
        this.endDate = endDate;
        //this.progress=0;
    }



 /*   public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }*/


    public void setAnyKey(String key,String value){
        switch (key){
            case "goalName":
                setGoalName(value);
                break;
            case "goalDescription":
                setGoalDescription(value);
                break;
            case "startDate":
                setStartDate(value);
                break;
            case "endDate":
                setEndDate(value);
                break;
        }
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    @Override
    public String toString() {
        return "Goal{" +
                "goalName='" + goalName + '\'' +
                ", goalDescription='" + goalDescription + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goal goal = (Goal) o;

        if (goalName != null ? !goalName.equals(goal.goalName) : goal.goalName != null)
            return false;
        if (goalDescription != null ? !goalDescription.equals(goal.goalDescription) : goal.goalDescription != null)
            return false;
        if (startDate != null ? !startDate.equals(goal.startDate) : goal.startDate != null)
            return false;
        return endDate != null ? endDate.equals(goal.endDate) : goal.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = goalName != null ? goalName.hashCode() : 0;
        result = 31 * result + (goalDescription != null ? goalDescription.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
