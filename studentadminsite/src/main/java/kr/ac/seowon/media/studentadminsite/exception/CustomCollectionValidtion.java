package kr.ac.seowon.media.studentadminsite.exception;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.Validation;
import java.util.Collection;

@Component
public class CustomCollectionValidtion implements Validator {

    private SpringValidatorAdapter validatorAdapter;

    public CustomCollectionValidtion(SpringValidatorAdapter validatorAdapter) {
        this.validatorAdapter = new SpringValidatorAdapter(Validation.buildDefaultValidatorFactory().getValidator());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Collection.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Collection collection = (Collection) o;
        for (Object object : collection) {
            validatorAdapter.validate(object,errors);
        }
    }
}
