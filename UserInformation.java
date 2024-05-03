/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол
Форматы данных:
фамилия, имя, отчество - строки
дата_рождения - строка формата dd.mm.yyyy
номер_телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.
Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки, 
обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры. Если форматы данных не 
совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои. 
Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться 
полученные данные, вида <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть 
стектрейс ошибки.
*/

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInformation {
    private String surname;
    private String name;
    private String patronymic;
    private Date birthDate;
    private long phoneNumber;
    private char gender;

    public UserInformation(String input) throws Exception {
        String[] data = input.split(" ");
        if (data.length != 6) {
            throw new Exception("Недостаточно данных. Вы ввели " + data.length + " полей, нужно 6.");
        }

        this.surname = data[0];
        this.name = data[1];
        this.patronymic = data[2];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            this.birthDate = dateFormat.parse(data[3]);
        } catch (ParseException e) {
            throw new Exception("Неверный формат даты. Введите дату в данном формате - dd.MM.yyyy");
        }

        try {
            this.phoneNumber = Long.parseLong(data[4]);
        } catch (NumberFormatException e) {
            throw new Exception("Неверный формат телефона. Введите целое число без знаков");
        }

        if (data[5].length() != 1 || (data[5].charAt(0) != 'f' && data[5].charAt(0) != 'm')) {
            throw new Exception("Неверный формат пола. Ожидается 'f' или 'm', получено " + data[5]);
        }
        this.gender = data[5].charAt(0);
    }

    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.surname + ".txt", true))) {
            writer.write(this.surname + " " + this.name + " " + this.patronymic + " " +
                    new SimpleDateFormat("dd.MM.yyyy").format(this.birthDate) + " " +
                    this.phoneNumber + " " + this.gender);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
