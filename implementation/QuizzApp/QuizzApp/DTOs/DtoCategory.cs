namespace QuizzApp.DTOs
{
    public class DtoCategory
    {
        public string name { get; set; }

        public System.Collections.Generic.IList<DtoQuestion> questions { get; set; }
    }
}