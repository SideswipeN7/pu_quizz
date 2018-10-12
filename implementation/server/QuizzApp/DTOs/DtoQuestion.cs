namespace QuizzApp.DTOs
{
    public class DtoQuestion
    {
        public string text { get; set; }
        public System.Collections.Generic.IList<DtoAnswer> answers { get; set; }
    }
}