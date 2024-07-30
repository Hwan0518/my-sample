package yourgroup.boilerplatelayered.global.common.enums;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Objects;

// BaseEnum<T,K>의 구현체인 Enum클래스와, T, K를 매개변수로 받아서 생성된다
public class BaseEnumConverter<E extends Enum<E> & BaseEnum<V, K>, V, K> implements AttributeConverter<E, V> {
    //필드값
    private final Class<E> eClass;

    //생성자
    protected BaseEnumConverter(Class<E> eClass) {
        this.eClass = eClass;
    }

    // Db에 저장할 때 -> Enum의 V(여기서는 코드)로 저장함
    @Override
    public V convertToDatabaseColumn(E attribute) {
        return attribute.getCode();
    }

    // Db에서 가져올 때 -> V(코드)를 받아서 Enum으로 변환해줌
    @Override
    public E convertToEntityAttribute(V dbData) {
        // db에서 가져온 값이 null일때
        if (Objects.isNull(dbData)) {
            return null;
        }
        return Arrays.stream(eClass.getEnumConstants())
                .filter(e -> e.getCode().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown code: " + dbData));
    }
}