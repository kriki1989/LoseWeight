package objectItems;

public class BMI {
	private double weight;
	private double height;
	private int age;
	private String gender;
	private double bmi;
	private String classification;
	private double bmr;

	//Constructor
	public BMI(double weight, double height, int age, String gender) {
		this.weight = weight;
		this.height = height;
		this.age = age;
		this.gender = gender;
		this.bmi = calculate_BMI();
		this.classification = calculate_classification();
		this.bmr = calculate_BMR();
	}

	public BMI(double weight, double height, int age, String gender, double bmi, String classification, double bmr) {
		this.weight = weight;
		this.height = height;
		this.age = age;
		this.gender = gender;
		this.bmi = bmi;
		this.classification = classification;
		this.bmr = bmr;
	}

	//Getters
	public double getWeight() {
		return weight;
	}
	public double getHeight() {
		return height;
	}
	public int getAge() {
		return age;
	}
	public String getGender() {
		return gender;
	}
	public double getBmi() {
		return bmi;
	}
	public String getClassification() {
		return classification;
	}
	public double getBmr() {
		return bmr;
	}

	//Setters
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	//Methods
	public double calculate_BMI(){
		double bmi = this.weight / (this.height*this.height);
		return Math.round(bmi * 100.0) / 100.0; 
	}

	public String calculate_classification(){
		String classification;
		if (this.bmi<18.5) {
			classification  = "Underweight";
		} else if (this.bmi<25) {
			classification  = "Optimal";
		} else if (this.bmi<30){
			classification  = "Overweight";
		} else {
			classification  = "Obese";
		}
		return classification; 
	}

	public double calculate_BMR(){
		double bmr;
		if (this.gender == "F") {
			bmr = 655+(9.6*this.weight)+(1.8*100*this.height)-(4.7*this.age);
		}else {
			bmr = 66+(13.7*this.weight)+(5*100*this.height)-(6.8*this.age);
		}
		return Math.round(bmr * 100.0) / 100.0;
	}

	//To string
	@Override
	public String toString() {
		return "Weight=" + weight + ", \nHeight=" + height + ", \nAge=" + age + ", \nGender=" + gender + ", \nBMI= " + bmi
				+ ", \nClassification=" + classification + ", \nBMR= " + bmr ;
	}

}