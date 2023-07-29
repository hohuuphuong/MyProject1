namespace nhom_13.data
{
    public class Article
    {
        public int Id { get; set; }

        public string Title { get; set; }

        public string Content { get; set; }

        public DateTime Time { get; set; }

        public Boolean IsHotNews { get; set; }

        public int CategoryId { get; set; }
        public Category Category { get; set; }

        public Image? Image { get; set; }
    }
}
