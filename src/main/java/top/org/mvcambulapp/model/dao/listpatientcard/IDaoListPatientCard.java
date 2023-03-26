package top.org.mvcambulapp.model.dao.listpatientcard;

import top.org.mvcambulapp.model.dao.IDaoBase;
import top.org.mvcambulapp.model.entity.ListPatientCard;

import java.util.List;

public interface IDaoListPatientCard extends IDaoBase<ListPatientCard> {
    List<ListPatientCard> getPatientCardByPatientId(Integer id);
}
