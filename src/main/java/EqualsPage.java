//Создам метод сравнения с сообщения в отдельном классе:
public class EqualsPage {
    //Инициализирую пустые переменные:
    String original = null;
    String nedebEquals = null;
    //Использую предустановленные переменные:
    public void equalsTest (String original, String nedebEquals) {
        //Применяю сравнение:
        if (nedebEquals.equals(original)) {
            //Сообщение если все хорошо:
            System.out.println("С " + nedebEquals + " все хорошо.");
        } else {
            //Сообщение если все плохо:
            System.out.println("С " + nedebEquals + " все плохо.");
        }
    };

}
