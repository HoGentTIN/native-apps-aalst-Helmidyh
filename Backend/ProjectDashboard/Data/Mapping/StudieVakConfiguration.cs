using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyAPI.Models.Domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace StudyAPI.Data.Mapping {
    public class StudieVakConfiguration : IEntityTypeConfiguration<StudieVak> {
        public void Configure(EntityTypeBuilder<StudieVak> builder) {
            builder.ToTable("StudieVak");
            builder.HasKey(x => x.StudieVakId);
            builder.Property(x => x.Name).IsRequired();
        }
    }
}
