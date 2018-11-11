CREATE TABLE [dbo].[Answers]
(
	[IdAnswer] INT NOT NULL PRIMARY KEY IDENTITY, 
	[IdQuestion] INT NOT NULL,
    [Text] NVARCHAR(50) NOT NULL, 
    [Value] BIT NOT NULL, 
    CONSTRAINT [FK_Answers_To_Questions] FOREIGN KEY ([IdQuestion]) REFERENCES [Questions]([IdQuestion])
)