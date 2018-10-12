using QuizzApp.DTOs;
using QuizzApp.Models;
using System;
using System.Data;
using System.Linq;
using System.Web.Http;
namespace QuizzApp.Controllers
{
    public class AndroidController : ApiController
    {
        private quizzDBEntities db = new quizzDBEntities();
        // GET: api/Android
        [HttpGet]
        [Route("Android")]
        public IQueryable<DtoCategory> Get()
        {
            try
            {
                return db.Categories.Where(c =>
                    db.Questions.Where(q => db.Answers.Where(a => c.CategoryId == q.CategoryId && q.QuestionId == a.QuestionId).Count() == 4).Count() > 0
                    ).Select(c => new DtoCategory()
                    {
                        name = c.Name,
                        questions = (db.Questions.Where(q => q.CategoryId == c.CategoryId).Select(q => new DtoQuestion()
                        {
                            text = q.Value,
                            answers = (db.Answers.Where(a => a.QuestionId == q.QuestionId).Select(a => new DtoAnswer()
                            {
                                text = a.Value,
                                isCorrect = a.Correct
                            }).ToList()
                        )
                        }).ToList()
                    ).ToList()
                    });
            }
            catch (Exception ex)
            {
                Console.Error.Write(ex);
                return null;
            }
        }
    }
}