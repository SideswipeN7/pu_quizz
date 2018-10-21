using QuizzApp.Models;
using System.Data.Entity;
using System.Net;
using System.Threading.Tasks;
using System.Web.Mvc;

namespace QuizzApp.Controllers
{
    public class TopTensController : Controller
    {
        private quizzDBEntities db = new quizzDBEntities();

        // GET: /TopTens/
        public async Task<ActionResult> Index()
        {
            return View(await db.TopTen.ToListAsync());
        }

        // GET: /TopTens/Details/5
        public async Task<ActionResult> Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TopTen topten = await db.TopTen.FindAsync(id);
            if (topten == null)
            {
                return HttpNotFound();
            }
            return View(topten);
        }

        // GET: /TopTens/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: /TopTens/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Create([Bind(Include = "Id,NickName,Points")] TopTen topten)
        {
            if (ModelState.IsValid)
            {
                db.TopTen.Add(topten);
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }

            return View(topten);
        }

        // GET: /TopTens/Edit/5
        public async Task<ActionResult> Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TopTen topten = await db.TopTen.FindAsync(id);
            if (topten == null)
            {
                return HttpNotFound();
            }
            return View(topten);
        }

        // POST: /TopTens/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> Edit([Bind(Include = "Id,NickName,Points")] TopTen topten)
        {
            if (ModelState.IsValid)
            {
                db.Entry(topten).State = EntityState.Modified;
                await db.SaveChangesAsync();
                return RedirectToAction("Index");
            }
            return View(topten);
        }

        // GET: /TopTens/Delete/5
        public async Task<ActionResult> Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            TopTen topten = await db.TopTen.FindAsync(id);
            if (topten == null)
            {
                return HttpNotFound();
            }
            return View(topten);
        }

        // POST: /TopTens/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteConfirmed(int id)
        {
            TopTen topten = await db.TopTen.FindAsync(id);
            db.TopTen.Remove(topten);
            await db.SaveChangesAsync();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}