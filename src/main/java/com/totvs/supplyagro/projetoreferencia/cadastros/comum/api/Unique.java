package com.totvs.supplyagro.projetoreferencia.cadastros.comum.api;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
@Documented
public @interface Unique {

    String message() default "{registro.ja.existe}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    /**
     * Atributo identificador da entidade<br>
     * Padrão "id"
     */
    String identificador() default "id";
    /**
     * Atributo da URL (@PathVariable) que identifica o ID (verbo PUT)<br>
     * Padrão "id"
     */
    String variavelUrl() default "id";
    /**
     * Entidade que será realizada a validação de unicidade
     */
    Class<?> entidade();
    /**
     * Atributo da entidade que é único
     */
    String atributo();
}
