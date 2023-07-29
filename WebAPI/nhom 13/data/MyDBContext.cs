using Microsoft.EntityFrameworkCore;

namespace nhom_13.data
{
    public class MyDBContext : DbContext
    {
        public MyDBContext(DbContextOptions options): base(options) { }

        #region DbSet
        public DbSet<Article> Articles { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<Image> Images { get; set; }
        #endregion 

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Article>(e =>
            {
                e.ToTable("Article");
                e.HasKey(ar => ar.Id);
                e.Property(ar => ar.Time).HasDefaultValueSql("getutcdate()");
                e.HasOne(e => e.Category)
                .WithMany(c => c.Articles)
                .HasForeignKey(e => e.CategoryId)
                .HasConstraintName("FK_Article_Category");
            });

            modelBuilder.Entity<Category>(e =>
            {
                e.ToTable("Category");
                e.HasKey(ar => ar.Id);
            });

            modelBuilder.Entity<Image>(e =>
            {
                e.ToTable("Image");
                e.HasKey(img => img.Id);
                e.HasOne(img => img.Article)
                .WithOne(ar => ar.Image)
                .HasForeignKey<Image>(img => img.ArticleId);
                //e.Ignore(img => img.File);

            });
        }
    }
}
