package com.ssurbar.survey.api.service;

import com.google.gson.Gson;
import com.ssurbar.survey.api.request.TemplateFilterListPostReq;
import com.ssurbar.survey.api.request.TemplatePostReq;
import com.ssurbar.survey.api.request.TemplateQuestionListPostReq;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.survey.Category;
import com.ssurbar.survey.db.entity.survey.FilterQuestion;
import com.ssurbar.survey.db.entity.survey.Question;
import com.ssurbar.survey.db.entity.survey.Template;
import com.ssurbar.survey.db.repository.survey.CategoryRepository;
import com.ssurbar.survey.db.repository.survey.FilterQuestionRepository;
import com.ssurbar.survey.db.repository.survey.QuestionRepository;
import com.ssurbar.survey.db.repository.survey.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService{

    @Autowired
    RandomIdUtil randomIdUtil;

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FilterQuestionRepository filterQuestionRepository;

    /* 새로운 설문서식 생성 */
    @Override
    public String createNewTemplate(TemplatePostReq templatePostReq) {

        String title = templatePostReq.getTitle();
        String description = templatePostReq.getDescription();
        String templateId = randomIdUtil.makeRandomId(13);
        Template template = Template.builder()
                .title(title)
                .description(description)
                .templateId(templateId)
                .build();

        return templateRepository.save(template).getTemplateId();
    }

    @Override
    public List<String> createNewQuestions(String templateId, TemplateQuestionListPostReq templateQuestionListPostReq) {

        Template template = templateRepository.getById(templateId);

        Gson gson = new Gson();

        // 카테고리 생성 및 저장
        List<Category> categorySaveList = new ArrayList<Category>();
        for(String categoryName : templateQuestionListPostReq.getCategoryList()){
            String categoryId = randomIdUtil.makeRandomId(13);
            categorySaveList.add(Category.builder()
                            .categoryId(categoryId)
                            .template(template)
                            .name(categoryName)
                    .build());
        }

        categorySaveList = categoryRepository.saveAll(categorySaveList);

        // 카테고리 이름을 key로 찾을 Category Entity
        HashMap<String, Category> categoryMap = new HashMap<>();
        for(Category category : categorySaveList){
            categoryMap.put(category.getName(), category);
        }

        // 문할 생성 및 저장
        List<Question> questionSaveList = new ArrayList<>();
        for(String questionJson : templateQuestionListPostReq.getQuestionList()){
            TemplateQuestionListPostReq.QuestionDto questionDto = gson.fromJson(questionJson, TemplateQuestionListPostReq.QuestionDto.class);

            // 문항의 카테고리
            Category category = categoryMap.get(questionDto.getCategoryName());
            String questionId = randomIdUtil.makeRandomId(13);
            questionSaveList.add(Question.builder()
                            .questionId(questionId)
                            .template(template)
                            .category(category)
                            .content(questionDto.getContent())
                            .isEssential(questionDto.getIsEssential())
                            .questionNum(questionDto.getNumber())
                            .questionType(questionDto.getQuestionType())
                            .title(questionDto.getTitle())
                    .build());
        }

        questionSaveList = questionRepository.saveAll(questionSaveList);

        List<String> idList = new ArrayList<>();
        for(Question question : questionSaveList){
            idList.add(question.getQuestionId());
        }

        return idList;
    }

    @Override
    public List<String> createNewFilters(String templateId, TemplateFilterListPostReq templateFilterListPostReq) {
        Template template = templateRepository.getById(templateId);

        Gson gson = new Gson();

        List<FilterQuestion> filterSaveList = new ArrayList<>();
        for(String filterJson : templateFilterListPostReq.getFilterQuestionList()){
            TemplateFilterListPostReq.FilterDto filterDto = gson.fromJson(filterJson, TemplateFilterListPostReq.FilterDto.class);

            String filterId = randomIdUtil.makeRandomId(13);
            filterSaveList.add(FilterQuestion.builder()
                            .filterQuestionId(filterId)
                            .questionNum(filterDto.getNumber())
                            .title(filterDto.getTitle())
                            .content(filterDto.getContent())
                            .template(template)
                    .build());
        }

        filterSaveList = filterQuestionRepository.saveAll(filterSaveList);

        List<String> idList = new ArrayList<>();
        for(FilterQuestion filter : filterSaveList){
            idList.add(filter.getFilterQuestionId());
        }

        return idList;
    }
}