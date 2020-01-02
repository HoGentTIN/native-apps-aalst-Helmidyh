using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyAPI.Models.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Data.Mapping {
    public class StudieTaskConfiguration : IEntityTypeConfiguration<StudieTask> {
        public void Configure(EntityTypeBuilder<StudieTask> builder) {
            builder.ToTable("StudieTask");
            builder.HasKey(x => x.StudieTaskId);

            builder.Property(x => x.StudieTaskTitle).IsRequired();
            builder.Property(x => x.VakName).IsRequired();

            builder.HasOne(x => x.vak)
                .WithMany()
                .HasForeignKey(x => x.VakId)
                .IsRequired().OnDelete(DeleteBehavior.Cascade);
        }
    }
}
