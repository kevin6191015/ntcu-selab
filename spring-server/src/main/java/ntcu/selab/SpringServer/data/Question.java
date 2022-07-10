package ntcu.selab.SpringServer.data;

public class Question {
    private int id = 0;
    private String name = null;
    private String description = null;
    private String image1 = null;
    private String image2 = null;
    private String[] input = new String[10];
    private String[] output = new String[10];
    private int inputornot = 0;

    public Question(String name, String description){
        this.name = name;
        this.description = description;
    }

    public Question(){
        
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getImage1(){
        return image1;
    }

    public void setImage1(String image1){
        this.image1 = image1;
    }

    public String getImage2(){
        return image2;
    }

    public void setImage2(String image2){
        this.image2 = image2;
    }

    public String[] getInput(){
        return input;
    }

    public void setInput(String[] input){
        for(int i=0 ; i<10 ; i++){
            this.input[i] = input[i];
        }
    }

    public String[] getOutnput(){
        return output;
    }

    public void setOutput(String[] output){
        for(int i=0 ; i<10 ; i++){
            this.output[i] = output[i];
        }
    }

    public int getInputornot(){
        return inputornot;
    }

    public void setInputornot(int inputornot){
        this.inputornot = inputornot;
    }
}
