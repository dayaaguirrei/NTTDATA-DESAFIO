
CREATE DATABASE [nttdata]
GO

USE [nttdata]
GO

-- PERSONAS

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[tb_personas]') AND type in (N'U'))
DROP TABLE [dbo].[tb_personas]
GO
CREATE TABLE [dbo].[tb_personas](
	[persona_id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[persona_direccion] [varchar](100) NULL,
	[persona_edad] [int] NULL,
	[persona_genero] [varchar](20) NULL,
	[persona_identi] [varchar](20) NULL,
	[persona_nombre] [varchar](100) NOT NULL,
	[persona_telefono] [varchar](20) NULL
)
GO

--- CLIENTES


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[tb_clientes]') AND type in (N'U'))
DROP TABLE [dbo].[tb_clientes]

CREATE TABLE [dbo].[tb_clientes](
	[cliente_id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[cliente_clave] [varchar](20) NULL,
	[cliente_estado] [bit] NOT NULL,
	[persona_id] [int] NULL,
	CONSTRAINT fk_persona FOREIGN KEY (persona_id) REFERENCES dbo.tb_personas (persona_id)
)

-- CUENTAS

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[tb_cuentas]') AND type in (N'U'))
DROP TABLE [dbo].[tb_cuentas]
GO
CREATE TABLE [dbo].[tb_cuentas](
	[cuentas_id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[cuentas_estado] [bit] NOT NULL,
	[cuentas_nocuenta] [varchar](50) NOT NULL,
	[cuentas_saldoini] [float] NOT NULL,
	[cuentas_tipocuenta] [int] NOT NULL,
	[cliente_id] [int] NULL,
	CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES dbo.tb_clientes (cliente_id)
)

-- MOVIMIENTOS
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[tb_movimientos]') AND type in (N'U'))
DROP TABLE [dbo].[tb_movimientos]
GO
CREATE TABLE [dbo].[tb_movimientos](
	[movimientos_id] [int] IDENTITY(1,1) NOT NULL PRIMARY KEY,
	[movimientos_estado] [bit] NOT NULL,
	[movimientos_fecha] [date] NOT NULL,
	[movimientos_nocuenta] [varchar](50) NOT NULL,
	[movimientos_saldo] [float] NOT NULL,
	[movimientos_tipomov] [int] NOT NULL,
	[movimientos_valor] [float] NOT NULL,
	[cuenta_id] [int] NULL,
	CONSTRAINT fk_cuenta FOREIGN KEY (cuenta_id) REFERENCES dbo.tb_cuentas (cuentas_id)
)



