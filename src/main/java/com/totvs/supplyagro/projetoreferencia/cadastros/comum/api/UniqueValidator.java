package com.totvs.supplyagro.projetoreferencia.cadastros.comum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private HttpServletRequest request;
    private Class<?> entidade;
    private String atributo;
    private String identificador;
    private String variavelUrl;

    @Override
    public void initialize(Unique anotacao) {
        entidade = anotacao.entidade();
        atributo = anotacao.atributo();
        identificador = anotacao.identificador();
        variavelUrl = anotacao.variavelUrl();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Boolean> query = criteriaBuilder.createQuery(Boolean.class);
        query.from(entidade);
        query.select(criteriaBuilder.literal(true));
        Subquery subquery = query.subquery(entidade);
        Root subRootEntity = subquery.from(entidade);
        subquery.select(subRootEntity);
        UUID idUrl = getIdUrl();
        if (Objects.nonNull(idUrl)) {
            subquery.where(criteriaBuilder.equal(subRootEntity.get(atributo), value),
                    criteriaBuilder.notEqual(subRootEntity.get(identificador), idUrl));
        } else {
            subquery.where(criteriaBuilder.equal(subRootEntity.get(atributo), value));
        }
        query.where(criteriaBuilder.exists(subquery));
        return entityManager.createQuery(query)
                .getResultList()
                .isEmpty();
    }

    private UUID getIdUrl() {
        Map<String, String> variaveis = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return Optional.ofNullable(variaveis.get(variavelUrl))
                .map(UUID::fromString)
                .orElse(null);
    }
}
