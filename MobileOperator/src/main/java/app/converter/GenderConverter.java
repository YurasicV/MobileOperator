package app.converter;

import app.entity.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.getId();
    }

    @Override
    public Gender convertToEntityAttribute(Integer id) {
        return Arrays.stream(Gender.values())
                .filter(a -> a.getId().equals(id)).findFirst().
                        orElse(null);
    }
}
