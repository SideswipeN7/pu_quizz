using QuizzApp.DTOs;
using QuizzApp.Models;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.Http;

namespace QuizzApp.Controllers
{
    [Route("Android")]
    public class AndroidController : ApiController
    {
        private quizzEntities db = new quizzEntities();

        // GET: api/Android/StartGame
        [HttpGet]
        [Route("StartGame")]
        public IQueryable<DtoCategory> GetGameStart()
        {
            try
            {
                return db.Categories.Where(c =>
                    db.Questions.Where(q => db.Answers.Where(a => c.IdCategory == q.IdCategory && q.IdQuestion == a.IdQuestion).Count() == 4).Count() > 0
                    ).Select(c => new DtoCategory()
                    {
                        name = c.Text,
                        questions = (db.Questions.Where(q => q.IdCategory == c.IdCategory).Select(q => new DtoQuestion()
                        {
                            text = q.Text,
                            answers = (db.Answers.Where(a => a.IdQuestion == q.IdQuestion).Select(a => new DtoAnswer()
                            {
                                text = a.Text,
                                isCorrect = a.Value
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

        // POST: api/Android/PostResult
        [HttpPost]
        [Route("PostResult")]
        public int PostGameResult(DtoGameData data)
        {
            try
            {
                var myScore = new Score() { Name = data.name, Value = data.value };
                db.Scores.Add(myScore);
                db.SaveChanges();
                List<Score> scores = db.Scores.OrderBy(s => s.Value).ToList();

                return scores.IndexOf(myScore) + 1;
            }
            catch (Exception ex)
            {
                Console.Error.Write(ex);
                return 0;
            }
        }

        // GET: api/Android/GetResult
        [HttpGet]
        [Route("GetResult")]
        public List<DtoGameData> GetGameResult()
        {
            try
            {
                List<Score> scores = db.Scores.OrderBy(s => s.Value).ToList();
                return scores.GetRange(0, 10).Select(s => new DtoGameData() { name = s.Name, value = s.Value }).ToList() ;
            }
            catch (Exception ex)
            {
                Console.Error.Write(ex);
                return null;
            }
        }
    }
}