package com.ssurbar.survey.api.service;

import com.google.gson.Gson;
import com.ssurbar.survey.api.request.TemplatePostReq;
import com.ssurbar.survey.api.request.TemplateQuestionListPostReq;
import com.ssurbar.survey.api.response.QuestionDetail;
import com.ssurbar.survey.api.response.TemplateInfo;
import com.ssurbar.survey.common.model.common.QuestionCreateResult;
import com.ssurbar.survey.common.util.RandomIdUtil;
import com.ssurbar.survey.db.entity.Code;
import com.ssurbar.survey.db.entity.survey.Category;
import com.ssurbar.survey.db.entity.survey.Question;
import com.ssurbar.survey.db.entity.survey.Template;
import com.ssurbar.survey.db.repository.CodeRepository;
import com.ssurbar.survey.db.repository.survey.CategoryRepository;
import com.ssurbar.survey.db.repository.survey.QuestionRepository;
import com.ssurbar.survey.db.repository.survey.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    CodeRepository codeRepository;

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
    public QuestionCreateResult createNewQuestions(String templateId, TemplateQuestionListPostReq templateQuestionListPostReq) {
        Template template = templateRepository.getById(templateId);

        if(template.getQuestions() != null && template.getQuestions().size() > 0) {
            return QuestionCreateResult.builder().isExist(true).build();
        }

        Gson gson = new Gson();

        // 카테고리 생성 및 저장
        List<Category> categorySaveList = new ArrayList<Category>();
        int categoryNum = 1;
        for(String categoryName : templateQuestionListPostReq.getCategoryList()){
            String categoryId = randomIdUtil.makeRandomId(13);
            categorySaveList.add(Category.builder()
                            .categoryId(categoryId)
                            .template(template)
                            .name(categoryName)
                            .number(categoryNum++)
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

            // 문항 타입
            Code questionType = codeRepository.getById(questionDto.getQuestionType());

            String questionId = randomIdUtil.makeRandomId(13);
            questionSaveList.add(Question.builder()
                            .questionId(questionId)
                            .template(template)
                            .category(category)
                            .content(questionDto.getContent())
                            .isEssential(questionDto.getIsEssential())
                            .questionNum(questionDto.getNumber())
                            .questionType(questionType)
                            .title(questionDto.getTitle())
                    .build());
        }

        questionSaveList = questionRepository.saveAll(questionSaveList);

        List<String> idList = new ArrayList<>();
        for(Question question : questionSaveList){
            idList.add(question.getQuestionId());
        }

        return QuestionCreateResult.builder().questionIdList(idList).isExist(false).build();
    }

    @Override
    public List<QuestionDetail> getQuestions(String templateId) {
        Optional<Template> templateOpt = templateRepository.getTemplateByTemplateId(templateId);

        if(!templateOpt.isPresent()) return null;
        Template template = templateOpt.get();

        List<Question> questions = template.getQuestions();

        return questions.stream().map(QuestionDetail::of).collect(Collectors.toList());
    }

    @Override
    public List<TemplateInfo> getDefaultTemplate() {
        List<Template> defaultTemplates = templateRepository.findByIsDefaultTrue();
        return defaultTemplates.stream().map(TemplateInfo::of).collect(Collectors.toList());
    }

    @Override
    public TemplateInfo getTemplate(String templateId) {
        Template template = templateRepository.findById(templateId).orElse(null);

        if(template == null) return null;

        return TemplateInfo.of(template);
    }
}