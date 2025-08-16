package io.github.edadma.readline

import io.github.edadma.readline.extern.Readline as rl

import scala.scalanative.libc.stdlib.free
import scala.scalanative.unsafe.*

def readline(prompt: String): String =
  Zone:
    val line = rl.readline(toCString(prompt))
    val res  = fromCString(line)

    free(line)
    res

def read_history(filename: String): Int =
  Zone:
    rl.read_history(toCString(filename))

def write_history(filename: String): Int =
  Zone:
    rl.write_history(toCString(filename))

def append_history(nelements: Int, filename: String): Int =
  Zone:
    rl.append_history(nelements, toCString(filename))

def add_history(line: String): Unit =
  Zone:
    rl.add_history(toCString(line))

def history_get(offset: Int): String =
  rl.history_get(offset) match
    case null => null
    case ptr  => fromCString(!ptr)

def history_base: Int = rl.history_base

def history_length: Int = rl.history_length
