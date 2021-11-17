import templateApi from '@/api/template.js';
import surveyApi from '@/api/survey.js';
import analysisApi from '@/api/analysis.js';

import Xlsx from 'xlsx';

export default {
    async downloadSurvey(surveyId){

        let templateId = null;
        await surveyApi.getSurveyDetailInfo(surveyId)
            .then( (res) => {
                // console.log(res.data);
                templateId = res.data.templateId;
            })
            .catch((err)=> {
                console.log(err);
            })
        console.log(templateId);
        
        // 설문 필터질문 가져오기
        var filterInfo = {};
        await surveyApi.getSurveyFilterInfo(surveyId)
            .then( (res) => {
                // console.log("필터질문가져오기");
                // console.log(res.data);
                res.data.filterQuestionList.forEach(el => {
                    // console.log(el.title);
                    filterInfo[el.title] = null;
                });
            })
            .catch((err)=> {
                console.log(err);
            });
        console.log(filterInfo);

        // 서식에 해당하는 질문가져오기
        var questionInfo = {};
        await templateApi.getTemplateQuestionList(templateId)
            .then( (res) => {
                // console.log("서식 질문가져오기")
                // console.log(res.data.questionList);
                res.data.questionList.forEach(el => {
                    questionInfo[el.questionId] = el.title;
                })
            })
            .catch((err)=> {
                console.log(err);
            });
        console.log(questionInfo);

        let rawList = [];
        // 설문응답 로우데이터 가져오기
        await analysisApi.getAnswerRawData(surveyId)
            .then( (res) => {
                console.log(res.data.filterDatas);

                res.data.filterDatas.forEach(filterData => {
                    const filterDataObj = JSON.parse(filterData.response)
                    console.log(filterDataObj)
                    // const question = {};

                    filterData.questionAnswers.forEach(answer => {
                        const title = questionInfo[answer.questionId];
                        filterDataObj[title] = JSON.parse(answer.response)[0];
                        
                    });
                    rawList.push(filterDataObj)
                    
                });
                
            })
            .catch((err)=> {
                console.log(err);
            });
        
        // 리스트만들기
        console.log("최종결과");
        console.log(rawList);
        this.makeExcel(rawList);
    },
    makeExcel(data){
        const workBook = Xlsx.utils.book_new();
        const workSheet = Xlsx.utils.json_to_sheet(data);
        Xlsx.utils.book_append_sheet(workBook, workSheet, 'example')
        Xlsx.writeFile(workBook, 'example.xlsx')
    }
}
