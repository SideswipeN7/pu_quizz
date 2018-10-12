using System.Web.Mvc;

namespace QuizzApp.Controllers
{
    public class TopTenController : Controller
    {
        // GET: TopTen
        public ActionResult Index()
        {
            return View();
        }
    }
}