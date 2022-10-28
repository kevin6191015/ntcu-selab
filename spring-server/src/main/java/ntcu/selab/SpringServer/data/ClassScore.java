package ntcu.selab.SpringServer.data;

public class ClassScore {
    private int people_correct = 0;
    private int people_answered = 0;
    private String day = null;
    private int answered_today = 0;
    private int sixty_down = 0;
    private int sixty_seventy = 0;
    private int seventy_eighty = 0;
    private int eighty_ninty = 0;
    private int ninty_hundred = 0;
    private int hunderd = 0;

    public ClassScore(){

    }

    public ClassScore(int sixty_down, int sixty_seventy, int seventy_eighty, int eighty_ninty, int ninty_hundred, int hunderd){
        this.sixty_down = sixty_down;
        this.sixty_seventy = sixty_seventy;
        this.seventy_eighty = seventy_eighty;
        this.eighty_ninty = eighty_ninty;
        this.ninty_hundred = ninty_hundred;
        this.hunderd = hunderd;
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

    public int getSixtyDown(){
        return this.sixty_down;
    }

    public void setSixtyDown(int sixty_down){
        this.sixty_down = sixty_down;
    }

    public int getSixtySeventy(){
        return this.sixty_seventy;
    }

    public void setSixtySeventy(int sixty_seventy){
        this.sixty_seventy = sixty_seventy;
    }

    public int getSeventyEighty(){
        return seventy_eighty;
    }

    public void setSeventyEighty(int seventy_eighty){
        this.seventy_eighty = seventy_eighty;
    }

    public int getEightyNinty(){
        return eighty_ninty;
    }

    public void setEightyNinty(int eighty_ninty){
        this.eighty_ninty = eighty_ninty;
    }

    public int getNinetyHunderd(){
        return this.ninty_hundred;
    }

    public void setNintyHunderd(int ninty_hundred){
        this.ninty_hundred = ninty_hundred;
    }

    public int getHundred(){
        return this.hunderd;
    }

    public void setHundred(int hunderd){
        this.hunderd = hunderd;
    }
}
