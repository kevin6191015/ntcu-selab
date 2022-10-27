package ntcu.selab.SpringServer.data;

public class ClassScore {
    private int people_correct = 0;
    private int people_answered = 0;
    private String day = null;
    private int answered_today = 0;

    public ClassScore(){

    }

    public int getPeopleCorrect(){
        return this.people_correct;
    }

    public void setPeopleCorrect(int people_correct){
        this.people_correct = people_correct;
    }

    public int getPeopleAnswered(){
        return this.people_answered;
    }

    public void setPeopleAnswered(int people_answered){
        this.people_answered = people_answered;
    }

    public String getDay(){
        return this.day;
    }

    public void setDay(String day){
        this.day = day;
    }

    public int getAnsweredToday(){
        return this.answered_today;
    }

    public void setAnsweredToday(int answered_today){
        this.answered_today = answered_today;
    }
}
